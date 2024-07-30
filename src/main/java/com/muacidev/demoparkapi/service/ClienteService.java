package com.muacidev.demoparkapi.service;

import com.muacidev.demoparkapi.entity.Cliente;
import com.muacidev.demoparkapi.exception.CpfUniqueViolationException;
import com.muacidev.demoparkapi.exception.UsernameUniqueViolationException;
import com.muacidev.demoparkapi.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}