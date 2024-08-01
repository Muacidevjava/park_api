package com.muacidev.demoparkapi.service;

import com.muacidev.demoparkapi.entity.ClienteVaga;
import com.muacidev.demoparkapi.exception.EntityNotFoundException;
import com.muacidev.demoparkapi.repository.ClienteVagaRepository;
import com.muacidev.demoparkapi.repository.projection.ClienteVagaProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;

@Service
@RequiredArgsConstructor
public class ClienteVagaService {
    private final ClienteVagaRepository repository;

    @Transactional
    public ClienteVaga salvar(ClienteVaga clienteVaga) {
        return repository.save(clienteVaga);
    }

    @Transactional(readOnly = true)
    public ClienteVaga bucarPorRecibo(String recibo) {
        return repository.findByReciboAndDataSaidaIsNull(recibo).orElseThrow(
                () -> new  EntityNotFoundException(
                        String.format("Recibo '%s' não encontrado no sistema ou check-ou jpa realizado", recibo)
                )
        );
    }

    @Transactional(readOnly = true)
    public long getTotalDeVezesEstacionamentoCompleto(String cpf) {
        return repository.countByClienteCpfAndDataSaidaIsNotNull(cpf);
    }

    @Transactional(readOnly = true)
    public Page<ClienteVagaProjection> buscarTodosPorClienteCpf(String cpf, Pageable pageable) {
        return repository.findAllByClienteCpf(cpf, pageable);
    }

    @Transactional(readOnly = true)
    public Page<ClienteVagaProjection> buscarTodosPorUsuarioId(Long id, Pageable pageable) {
        return repository.findAllByClienteUsuarioId(id, pageable);
    }
}
