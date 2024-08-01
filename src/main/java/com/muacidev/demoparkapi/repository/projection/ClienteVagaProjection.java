package com.muacidev.demoparkapi.repository.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface ClienteVagaProjection {

     String getplaca();

     String getmarca();

     String getmodelo();

     String getcor();

     String getclienteCpf();

      String getrecibo();

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
     LocalDateTime getdataEntrada();

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
     LocalDateTime getdataSaida();

     String getvagaCodigo();

     BigDecimal getvalor();

     BigDecimal getdesconto();
}
