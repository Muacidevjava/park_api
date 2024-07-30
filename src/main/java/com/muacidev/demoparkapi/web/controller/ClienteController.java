package com.muacidev.demoparkapi.web.controller;

import com.muacidev.demoparkapi.entity.Cliente;
import com.muacidev.demoparkapi.jwt.JwtUserDetails;
import com.muacidev.demoparkapi.service.ClienteService;
import com.muacidev.demoparkapi.service.UsuarioService;
import com.muacidev.demoparkapi.web.dto.ClienteCreateDto;
import com.muacidev.demoparkapi.web.dto.ClienteResponseDto;
import com.muacidev.demoparkapi.web.dto.UsuarioResponseDto;
import com.muacidev.demoparkapi.web.dto.mapper.ClienteMapper;
import com.muacidev.demoparkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Clientes", description = "Contém todas as operaçoes relativa aos recursos para cadastro, edição  de leitura de um cliente")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final UsuarioService usuarioService;

    @Operation(summary = "Criar um novo Cliente", description = "Recurso para criar um novo Client vinculado a um usuario cadastrado. " +
            "Requisição exige um Bearer Token. Acesso restrito a Role='CLIENTE'",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json;charset=utf-8", schema = @Schema(implementation = ClienteResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Cliente CPF já possui cadastro no sistema",
                            content = @Content(mediaType = "application/json;charset=utf-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada invalidos",
                            content = @Content(mediaType = "application/json;charset=utf-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permitido ao perfil de ADMIN",
                            content = @Content(mediaType = "application/json;charset=utf-8", schema = @Schema(implementation = ErrorMessage.class)))
            })

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
    @GetMapping("{id}")
    public ResponseEntity<ClienteResponseDto> getById(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        return ResponseEntity.ok(ClienteMapper.toDto(cliente));
    }


}
