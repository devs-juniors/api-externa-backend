package com.repositories;

import com.domains.Carteira;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {
    List<Carteira> findByCorretoraId(Long corretoraId);
    boolean existsByNomeAndCorretoraId(String nome, Long corretoraId);
    boolean existsByCorretoraId(Long corretoraId);
}