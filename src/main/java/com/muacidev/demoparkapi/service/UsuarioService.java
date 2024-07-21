package com.muacidev.demoparkapi.service;

import com.muacidev.demoparkapi.entity.Usuario;
import com.muacidev.demoparkapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UsuarioService {
    private  final UsuarioRepository usuarioRepository;

   @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorID(Long id) {
       return usuarioRepository.findById(id).orElseThrow(
               () -> new RuntimeException("Usuário não encontrado")
       );
    }

    @Transactional
    public Usuario editarSenha(Long id, String password) {
      Usuario user = buscarPorID(id);
      user.setPassword(password);
      return user;
    }
}
