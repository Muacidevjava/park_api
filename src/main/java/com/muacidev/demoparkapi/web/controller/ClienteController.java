package com.muacidev.demoparkapi.web.controller;

import com.muacidev.demoparkapi.entity.Cliente;
import com.muacidev.demoparkapi.jwt.JwtUserDetails;
import com.muacidev.demoparkapi.service.ClienteService;
import com.muacidev.demoparkapi.service.UsuarioService;
import com.muacidev.demoparkapi.web.dto.ClienteCreateDto;
import com.muacidev.demoparkapi.web.dto.ClienteResponseDto;
import com.muacidev.demoparkapi.web.dto.mapper.ClienteMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final UsuarioService usuarioService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<ClienteResponseDto> create(@RequestBody @Valid ClienteCreateDto dto
            , @AuthenticationPrincipal JwtUserDetails userDetails) {
        Cliente cliente = ClienteMapper.toCliente(dto);
        cliente.setUsuario(usuarioService.buscarPorId(userDetails.getId()));
        cliente = clienteService.salvar(cliente);
        return ResponseEntity
                .status(201)
                .body(ClienteMapper.toDto(cliente));

    }


}
