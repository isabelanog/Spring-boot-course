package com.treina.recife.sgp.controller;


import com.treina.recife.sgp.constants.StatusTarefa;
import com.treina.recife.sgp.dto.TarefaDto;
import com.treina.recife.sgp.model.Projeto;
import com.treina.recife.sgp.model.Tarefa;
import com.treina.recife.sgp.model.Usuario;
import com.treina.recife.sgp.service.ProjetoService;
import com.treina.recife.sgp.service.TarefaService;
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
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    TarefaService tarefaService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ProjetoService projetoService;

    Logger logger = LogManager.getLogger(TarefaController.class);

    public TarefaController(TarefaService tarefaService, UsuarioService usuarioService, ProjetoService projetoService) {

        this.tarefaService = tarefaService;
        this.usuarioService = usuarioService;
        this.projetoService = projetoService;
    }

    @GetMapping
    public ResponseEntity<Page<Tarefa>> getTarefas(@PageableDefault(sort = "taskId", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<Tarefa> tarefas = tarefaService.getTarefas(pageable);

        if (tarefas.isEmpty()) {
            logger.info("Ainda não há tarefa cadastrada.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Page.empty());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(tarefas);
        }
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Object> getTarefaById(@PathVariable(value = "taskId") long taskId) {

        Optional<Tarefa> tarefa = tarefaService.getTarefaById(taskId);

        if (tarefa.isEmpty()) {
            logger.error("Tarefa não encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada");
        } else {
            logger.info(tarefa.get().toString());
            return ResponseEntity.status(HttpStatus.OK).body(tarefa.get());
        }
    }

    @PostMapping()
    public ResponseEntity<Object> createTarefa(@RequestBody TarefaDto tarefaDto) {

        Tarefa novaTarefa = new Tarefa();
        novaTarefa.setTitulo(tarefaDto.getTitulo());
        novaTarefa.setDataCriacao(tarefaDto.getDataCriacao());
        novaTarefa.setDataConclusao(tarefaDto.getDataConclusao());
        novaTarefa.setStatus(tarefaDto.getStatus());
        novaTarefa.setPrioridade(tarefaDto.getPrioridade());

        Optional<Usuario> responsavelTarefa = usuarioService.getUsuarioById(tarefaDto.getUserId());

        if (responsavelTarefa.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário inexistente");
        }
        novaTarefa.setUsuario(responsavelTarefa.get());

        Optional<Projeto> projeto = projetoService.getProjectById(tarefaDto.getProjectId());

        if (projeto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto inexistente");
        }

        novaTarefa.setProjeto(projeto.get());

        Tarefa tarefa = tarefaService.createTarefa(novaTarefa);

        logger.info("Tarefa {} criado com sucesso.", novaTarefa.getTitulo());

        return ResponseEntity.status(HttpStatus.CREATED).body(tarefa);

    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Object> updateTarefa(@PathVariable(value = "taskId") long taskId,
                                               @RequestBody TarefaDto tarefaDto) {

        //verificar se a tarefa existe no banco de dados

        Optional<Tarefa> tarefa = tarefaService.getTarefaById(taskId);

        // se não existir, retornar que a tarefa não foi encontrada
        if (tarefa.isEmpty()) {
            logger.error("Tarefa não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrado");
        }
        //Se existir, atualizar a tarefa


        //Verificar se o usuário existe na base de dados

        Optional<Usuario> responsavelTarefa = usuarioService.getUsuarioById(tarefaDto.getUserId());

        if (responsavelTarefa.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário inexistente");
        }
        //verificar ser o Projeto existe na base de dados

        Optional<Projeto> projeto = projetoService.getProjectById(tarefaDto.getProjectId());

        //se o projeto não existir, retornar projeto não encontrado
        if (projeto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto inexistente");
        }

        Tarefa tarefaAtualizada = new Tarefa();

        tarefaAtualizada.setTitulo(tarefaDto.getTitulo());
        tarefaAtualizada.setStatus(tarefaDto.getStatus());
        tarefaAtualizada.setPrioridade(tarefaDto.getPrioridade());
        tarefaAtualizada.setDataCriacao(tarefaDto.getDataCriacao());
        tarefaAtualizada.setDataConclusao(tarefaDto.getDataConclusao());
        tarefaAtualizada.setUsuario(responsavelTarefa.get());
        tarefaAtualizada.setProjeto(projeto.get());

        tarefaService.updateTarefa(tarefaAtualizada);

        logger.info("Tarefa {} atualizada com sucesso.", tarefaAtualizada.getTitulo());

        return ResponseEntity.status(HttpStatus.OK).body(tarefaAtualizada);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Object> deleteTarefa(@PathVariable(value = "taskId") long taskId) {

        Optional<Tarefa> tarefa = tarefaService.getTarefaById(taskId);

        if (tarefa.isEmpty()) {
            logger.warn("Tarefa não encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada");
        } else {
            tarefaService.deleteTarefaById(taskId);
            logger.info("Tarefa {} deletada com sucesso", taskId);
            return ResponseEntity.status(HttpStatus.OK).body("Tarefa deletada com sucesso");
        }
    }

    @PatchMapping("{taskId}/status")
    public ResponseEntity<Object> atualizarStatus(@PathVariable(value = "taskId") long taskId,
                                                  @RequestBody Map<String, String> body) {

        Optional<Tarefa> tarefa = tarefaService.getTarefaById(taskId);

        if (tarefa.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada.");
        }

        String statusBody = body.get("status");

        if (statusBody == null) {
            return ResponseEntity.badRequest().body("Status é obrigatório.");
        }

        StatusTarefa novoStatus;

        try {
            novoStatus = StatusTarefa.valueOf(statusBody.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Status inválido. Valores permitidos: PENDENTE, FAZENDO ou FINALIZADA");
        }

        Tarefa tarefaAtualizada = tarefa.get();
        tarefaAtualizada.setStatus(novoStatus);
        tarefaService.updateTarefa(tarefaAtualizada);
        logger.info("Status atualizado com sucesso.");

        return ResponseEntity.status(HttpStatus.OK).body(tarefaAtualizada);
    }

}
