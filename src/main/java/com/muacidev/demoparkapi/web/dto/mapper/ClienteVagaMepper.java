package com.muacidev.demoparkapi.web.dto.mapper;

import com.muacidev.demoparkapi.entity.ClienteVaga;
import com.muacidev.demoparkapi.web.dto.EstacionamentoCreateDto;
import com.muacidev.demoparkapi.web.dto.EstacionamentoResponseDto;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ClienteVagaMepper {

    public static ClienteVaga toClienteVaga(EstacionamentoCreateDto dto) {
        return new ModelMapper().map(dto, ClienteVaga.class);
    }

    public static EstacionamentoResponseDto toDto(ClienteVaga clienteVaga) {
        return new ModelMapper().map(clienteVaga, EstacionamentoResponseDto.class);
    }
}
