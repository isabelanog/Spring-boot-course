package com.treina.recife.sgp.controller;

import com.treina.recife.sgp.constants.StatusProjeto;
import com.treina.recife.sgp.dto.ProjetoDto;
import com.treina.recife.sgp.dto.UsuarioDto;
import com.treina.recife.sgp.model.Projeto;
import com.treina.recife.sgp.model.Usuario;
import com.treina.recife.sgp.service.ProjetoService;
import com.treina.recife.sgp.service.UsuarioService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {

    @Autowired
    ProjetoService projetoService;

    @Autowired
    UsuarioService usuarioService;

    Logger logger = LogManager.getLogger(ProjetoController.class);

    public ProjetoController(ProjetoService projetoService, UsuarioService usuarioService) {

        this.projetoService = projetoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<Page<Projeto>> getProjetos(@PageableDefault(sort = "projectId", 
                                                    direction = Sort.Direction.ASC) Pageable pageable) {

        Page<Projeto> projetos = projetoService.getProjetos(pageable);

        if (projetos.isEmpty()) {
            logger.info("Ainda não há projeto cadastrado.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Page.empty());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(projetos);
        }
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Object> getProjetoById(@PathVariable(value = "projectId") long projectId) {

        Optional<Projeto> projeto = projetoService.getProjectById(projectId);

        if (projeto.isEmpty()) {
            logger.error("Projeto não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado");
        } else {
            logger.info(projeto.get().toString());
            return ResponseEntity.status(HttpStatus.OK).body(projeto.get());
        }
    }

    @PostMapping()
    public ResponseEntity<Object> createProjeto(@RequestBody ProjetoDto projetoDto) {

        Projeto novoProjeto = new Projeto();
        novoProjeto.setNome(projetoDto.getNome());
        novoProjeto.setDescricao(projetoDto.getDescricao());
        novoProjeto.setDataInicio(projetoDto.getDataInicio());
        novoProjeto.setStatus(StatusProjeto.ATIVO);
        novoProjeto.setDataConclusao(projetoDto.getDataConclusao());

        Projeto projeto = projetoService.createProjeto(novoProjeto);
        logger.info("Projeto {} criado com sucesso.", novoProjeto.getNome());
        return ResponseEntity.status(HttpStatus.CREATED).body(projeto);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<Object> updateProjeto(@PathVariable(value = "projectId") long projectId,
                                                @RequestBody ProjetoDto projetoDto) {

        Optional<Projeto> projeto = projetoService.getProjectById(projectId);

        if (projeto.isEmpty()) {
            logger.error("Projeto não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado");
        } else {
            Projeto projetoAtualizado = projeto.get();
            
            projetoAtualizado.setNome(projetoDto.getNome());
            projetoAtualizado.setDescricao(projetoDto.getDescricao());
            projetoAtualizado.setDataInicio(projetoDto.getDataInicio());
            projetoAtualizado.setDataConclusao(projetoDto.getDataConclusao());
            projetoAtualizado.setStatus(projetoDto.getStatus());

            projetoService.updateProjeto(projetoAtualizado);
            logger.info("Projeto de id {} atualizado com sucesso", projetoAtualizado.getProjectId());

            return ResponseEntity.status(HttpStatus.OK).body(projetoAtualizado);
        }
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Object> deleteProjeto(@PathVariable(value = "projectId") long projectId) {

        Optional<Projeto> projeto = projetoService.getProjectById(projectId);

        if (projeto.isEmpty()) {
            logger.warn("Projeto não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado");
        } else {
            projetoService.deleteProjeto(projectId);
            logger.info("Projeto {} deletado com sucesso", projectId);
            return ResponseEntity.status(HttpStatus.OK).body("Projeto deletado com sucesso");
        }
    }

    @PatchMapping("/{projectId}/responsavel")
    public ResponseEntity<Object> atribuirResponsavel(@PathVariable(value = "projectId") long projectId,
                                                      @RequestBody() UsuarioDto usuarioDto) {

        Optional<Projeto> projetoOptional = projetoService.getProjectById(projectId);

        if (projetoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado.");
        }

        Optional<Usuario> usuarioOptional = usuarioService.getUsuarioById(usuarioDto.getUserId());

        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }

        Projeto projeto = projetoOptional.get();
        Usuario responsavel = usuarioOptional.get();

        projeto.setResponsavel(responsavel);

        Projeto projetoAtualizado = projetoService.updateProjeto(projeto);
        logger.info("Responsável {} designado ao projeto {} com sucesso", responsavel, projeto.getNome());

        return ResponseEntity.status(HttpStatus.OK).body(projetoAtualizado);
    }

    @PatchMapping("{projectId}/status")
    public ResponseEntity<Object> atualizarStatus(@PathVariable(value = "projectId") long projectId,
                                                  @RequestBody Map<String, String> body) {

        Optional<Projeto> projeto = projetoService.getProjectById(projectId);

        if (projeto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado.");
        }

        String statusBody = body.get("status");

        if (statusBody == null) {
            return ResponseEntity.badRequest().body("Status é obrigatório.");
        }

        StatusProjeto novoStatus;

        try {
            novoStatus = StatusProjeto.valueOf(statusBody.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Status inválido. Valores permitidos: ATIVO, CONCLUIDO, CANCELADO");
        }

        Projeto projetoAtualizado = projeto.get();
        projetoAtualizado.setStatus(novoStatus);
        projetoService.createProjeto(projetoAtualizado);
        logger.info("Status do Projeto atualizado com sucesso.");

        return ResponseEntity.status(HttpStatus.OK).body(projetoAtualizado);
    }

}
