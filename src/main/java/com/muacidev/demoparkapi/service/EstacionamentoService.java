package com.muacidev.demoparkapi.service;

import com.muacidev.demoparkapi.entity.Cliente;
import com.muacidev.demoparkapi.entity.ClienteVaga;
import com.muacidev.demoparkapi.entity.Vaga;
import com.muacidev.demoparkapi.exception.EntityNotFoundException;
import com.muacidev.demoparkapi.repository.VagaRepository;
import com.muacidev.demoparkapi.util.EstacionamentoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.muacidev.demoparkapi.entity.Vaga.StatusVaga.LIVRE;
import static com.muacidev.demoparkapi.entity.Vaga.StatusVaga.OCUPADA;

@RequiredArgsConstructor
@Service
public class EstacionamentoService {

    private final ClienteVagaService clienteVagaService;
    private final ClienteService clienteService;
    private final VagaService vagaService;
    private final VagaRepository vagaRepository;

    @Transactional
    public ClienteVaga chekin(ClienteVaga clienteVaga) {

        Cliente cliente = clienteService.buscarPorCpf(clienteVaga.getCliente().getCpf());
       clienteVaga.setCliente(cliente);

        Vaga vaga = vagaService.buscarPorVagaLivre();
        vaga.setStatus(OCUPADA);
        clienteVaga.setVaga(vaga);

        clienteVaga.setDataEntrada(LocalDateTime.now());
        clienteVaga.setRecibo(EstacionamentoUtils.gerarRecibo());

        clienteVagaService.salvar(clienteVaga);
        return clienteVaga;

    }

}
