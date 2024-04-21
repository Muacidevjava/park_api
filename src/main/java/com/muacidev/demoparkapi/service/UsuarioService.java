package com.muacidev.demoparkapi.service;

import com.muacidev.demoparkapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;

}
