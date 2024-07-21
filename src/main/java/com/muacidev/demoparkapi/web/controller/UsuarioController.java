package com.muacidev.demoparkapi.web.controller;

import com.muacidev.demoparkapi.entity.Usuario;
import com.muacidev.demoparkapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario){
        Usuario user = usuarioService.salvar(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getByID(@PathVariable Long id){
        Usuario user = usuarioService.buscarPorID(id);

        return ResponseEntity.ok(user);

    }
}
