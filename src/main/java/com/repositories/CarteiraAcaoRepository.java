package com.repositories;

import com.domains.CarteiraAcao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarteiraAcaoRepository extends JpaRepository<CarteiraAcao, Long> {
    Optional<CarteiraAcao> findByCarteiraIdAndAcaoId(Long carteiraId, Long acaoId);
    boolean existsByCarteiraIdAndAcaoId(Long carteiraId, Long acaoId);
    boolean existsByAcaoId(Long acaoId);
}
