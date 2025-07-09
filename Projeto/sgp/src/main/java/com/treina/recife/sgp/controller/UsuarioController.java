package com.treina.recife.sgp.controller;

import com.treina.recife.sgp.constants.StatusUsuario;
import com.treina.recife.sgp.dto.UsuarioDto;
import com.treina.recife.sgp.model.Usuario;
import com.treina.recife.sgp.service.UsuarioService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UsuarioController {

    private final UsuarioService usuarioService;

    Logger logger = LogManager.getLogger(UsuarioController.class);

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<Page<Usuario>> getUsuarios(@PageableDefault(sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<Usuario> users = usuarioService.getUsuarios(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(users);
    }


    @GetMapping("/{usuarioId}")
    public ResponseEntity<Object> getUsuarioById(@PathVariable(value = "usuarioId") long usuarioId) {

            Optional<Usuario> usuarioOptional = usuarioService.getUsuarioById(usuarioId);

            if (usuarioOptional.isEmpty()) {
                logger.warn("Usuário não encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
            } else {
                logger.info(usuarioOptional.get().toString());
                return ResponseEntity.status(HttpStatus.OK).body(usuarioOptional.get());
            }
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody UsuarioDto usuarioDto) {

        if (usuarioService.isEmailAlreadyTaken(usuarioDto.getEmail())) {
            logger.error("{} email já está em uso.", usuarioDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(usuarioDto.getEmail() + " email já está em uso.");
        }

        Usuario usuario = new Usuario();
        usuario.
        usuarioService.createUsuario(usuario);
        logger.info("Usuário {} criado com sucesso", usuario.);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

}
