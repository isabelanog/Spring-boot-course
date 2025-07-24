package com.treina.recife.sgp.controller;

import com.treina.recife.sgp.constants.StatusUsuario;
import com.treina.recife.sgp.dto.UsuarioDto;
import com.treina.recife.sgp.model.Usuario;
import com.treina.recife.sgp.service.UsuarioService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {

        this.usuarioService = usuarioService;
    }

    Logger logger = LogManager.getLogger(UsuarioController.class);

    @GetMapping
    public ResponseEntity<Page<Usuario>> getUsuarios(@PageableDefault(sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<Usuario> usuarios = usuarioService.getUsuarios(pageable);

        if (usuarios.isEmpty()) {
            logger.info("Ainda não há usuários cadastrados.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Page.empty());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(usuarios);
        }
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<Object> getUsuarioById(@PathVariable(value = "usuarioId") long usuarioId) {

        Optional<Usuario> usuario = usuarioService.getUsuarioById(usuarioId);

        if (usuario.isEmpty()) {
            logger.error("Usuário não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        } else {
            logger.info(usuario.get().toString());
            return ResponseEntity.status(HttpStatus.OK).body(usuario.get());
        }
    }

    @PostMapping()
    public ResponseEntity<Object> createUsuario(@RequestBody UsuarioDto usuarioDto) {

    boolean emailAlreadyTaken = usuarioService.isEmailAlreadyTaken(usuarioDto.getEmail());

        if (emailAlreadyTaken) {
            logger.error("{} já está em uso", usuarioDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(usuarioDto.getEmail() + " já está em uso");
        } else {
            Usuario novoUsuario = new Usuario();
            novoUsuario.setNome(usuarioDto.getNome());
            novoUsuario.setCpf(usuarioDto.getCpf());
            novoUsuario.setEmail(usuarioDto.getEmail());
            novoUsuario.setSenha(usuarioDto.getSenha());
            novoUsuario.setDataNascimento(usuarioDto.getDataNascimento());
            novoUsuario.setStatus(StatusUsuario.ATIVO);

            usuarioService.createUsuario(novoUsuario);

            logger.info("Usuário {} criado com sucesso", novoUsuario.getUserId());

            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUsuario(@PathVariable(value = "userId") long userId,
                                                @RequestBody UsuarioDto usuarioDto) {

        Optional<Usuario> usuario = usuarioService.getUsuarioById(userId);

        if (usuario.isEmpty()) {
            logger.error("Usuário não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        } else {
            Usuario usuarioAtualizado = usuario.get();
            usuarioAtualizado.setNome(usuarioDto.getNome());
            usuarioAtualizado.setCpf(usuarioDto.getCpf());
            usuarioAtualizado.setEmail(usuarioDto.getEmail());
            usuarioAtualizado.setDataNascimento(usuarioDto.getDataNascimento());
            usuarioAtualizado.setStatus(usuarioDto.getStatus());

            usuarioService.updateUsuario(usuarioAtualizado);

            logger.info("Usuário de ID {} atualizado com sucesso", usuarioAtualizado.getUserId());

            return ResponseEntity.status(HttpStatus.OK).body(usuarioAtualizado);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUsuario(@PathVariable(value = "userId") long userId) {

        Optional<Usuario> usuario = usuarioService.getUsuarioById(userId);

        if (usuario.isEmpty()) {
            logger.warn("Usuário não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        } else {
            usuarioService.deleteUsuario(userId);
            logger.info("Usuário {} deletado com sucesso", userId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuário deletado com sucesso");
        }
    }

    @PatchMapping("/{userId}/status")
    public ResponseEntity<Object> atualizarStatus(@PathVariable(value = "userId") long userId,
                                                  @RequestBody Map<String, String> body) {

        Optional<Usuario> usuario = usuarioService.getUsuarioById(userId);
        
        if (usuario.isEmpty()) {
            logger.error("Usuário não encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }

        String statusBody = body.get("status");
        
        if (statusBody == null) {
            return ResponseEntity.badRequest().body("Status é obrigatório.");
        }

        StatusUsuario novoStatus;

        try {
            novoStatus = StatusUsuario.valueOf(statusBody.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.info(e.getMessage());
            return ResponseEntity.badRequest().body("Status inválido. Valores permitidos: ATIVO, INATIVO");
        }

        Usuario usuarioAtualizado = usuario.get();
        usuarioAtualizado.setStatus(novoStatus);

        usuarioService.updateUsuario(usuarioAtualizado);
        logger.info("Status do usuário atualizado com sucesso.");

        return ResponseEntity.status(HttpStatus.OK).body(usuarioAtualizado);
    }
}
