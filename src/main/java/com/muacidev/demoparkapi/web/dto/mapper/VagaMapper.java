package com.muacidev.demoparkapi.web.dto.mapper;

import com.muacidev.demoparkapi.entity.Vaga;
import com.muacidev.demoparkapi.web.dto.VagaCreateDto;
import com.muacidev.demoparkapi.web.dto.VagaResponseDto;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class VagaMapper {
    public static Vaga toVaga(VagaCreateDto  dto) {
        return new ModelMapper().map(dto,Vaga.class);

    }
    public static VagaResponseDto toDto(Vaga vaga) {
        return new ModelMapper().map(vaga, VagaResponseDto.class);
    }
}
