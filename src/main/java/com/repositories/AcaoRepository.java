package com.repositories;

import com.domains.Acao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AcaoRepository extends JpaRepository<Acao, Long> {

    Optional<Acao> findByTicker(String ticker);
    boolean existsByTicker(String ticker);
}
