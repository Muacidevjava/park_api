package com.muacidev.demoparkapi.service;

import com.muacidev.demoparkapi.entity.Usuario;
import com.muacidev.demoparkapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;
   @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}
