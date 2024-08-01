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

import java.math.BigDecimal;
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

        return clienteVagaService.salvar(clienteVaga);


    }

    @Transactional
    public ClienteVaga checkout(String recibo) {
        ClienteVaga clienteVaga = clienteVagaService.bucarPorRecibo(recibo);
        LocalDateTime dataSaida = LocalDateTime.now();

        BigDecimal valor = EstacionamentoUtils.calcularCusto(clienteVaga.getDataEntrada(), dataSaida);
        clienteVaga.setValor(valor);

        long totaldevezes = clienteVagaService.getTotalDeVezesEstacionamentoCompleto(clienteVaga.getCliente().getCpf());

        BigDecimal desconto = EstacionamentoUtils.calcularDesconto(valor, totaldevezes);
        clienteVaga.setDesconto(desconto);

        clienteVaga.setDataSaida(dataSaida);
        clienteVaga.getVaga().setStatus(Vaga.StatusVaga.LIVRE);

        return clienteVagaService.salvar(clienteVaga);

    }
}
