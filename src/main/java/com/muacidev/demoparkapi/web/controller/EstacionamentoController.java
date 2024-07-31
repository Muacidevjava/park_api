package com.muacidev.demoparkapi.web.controller;

import com.muacidev.demoparkapi.entity.ClienteVaga;
import com.muacidev.demoparkapi.service.EstacionamentoService;
import com.muacidev.demoparkapi.web.dto.EstacionamentoCreateDto;
import com.muacidev.demoparkapi.web.dto.EstacionamentoResponseDto;
import com.muacidev.demoparkapi.web.dto.mapper.ClienteMapper;
import com.muacidev.demoparkapi.web.dto.mapper.ClienteVagaMepper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/estacionamento")
public class EstacionamentoController {
    private final EstacionamentoService estacionamentoService;

    @PostMapping("/check-in")
     @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstacionamentoResponseDto> chekin (@RequestBody @Valid EstacionamentoCreateDto dto) {
        ClienteVaga clienteVaga = ClienteVagaMepper.toClienteVaga(dto);
        estacionamentoService.chekin(clienteVaga);
        EstacionamentoResponseDto responseDto = ClienteVagaMepper.toDto(clienteVaga);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{recibo}")
                .buildAndExpand(clienteVaga.getRecibo())
                .toUri();
        return ResponseEntity.created(location).body(responseDto);

    }
}
