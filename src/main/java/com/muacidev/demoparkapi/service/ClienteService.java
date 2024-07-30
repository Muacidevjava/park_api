package com.muacidev.demoparkapi.service;

import com.muacidev.demoparkapi.entity.Cliente;
import com.muacidev.demoparkapi.exception.CpfUniqueViolationException;
import com.muacidev.demoparkapi.repository.ClienteRepository;
import com.muacidev.demoparkapi.repository.projection.ClienteProjection;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional
    public Cliente salvar(Cliente cliente) {
        try {
            return clienteRepository.save(cliente);
        } catch (DataAccessException ex) {
            throw new CpfUniqueViolationException(
                    String.format("Cpf '%s' não pode ser cadastrado, já existe no sistema.",
                            cliente.getCpf()));
        }
    }

@Transactional(readOnly = true)
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Cliente com id '%s' não encontrado.", id))
        );
    }

    @Transactional(readOnly = true)
    public Page<ClienteProjection> buscarTodos(Pageable pageable) {
        return clienteRepository.findAllPageable(pageable);
    }
}
