package com.muacidev.demoparkapi.repository;

import com.muacidev.demoparkapi.entity.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VagaRepository extends JpaRepository <Vaga, Long> {
}
