package com.repositories;

import com.domains.Corretora;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CorretoraRepository extends JpaRepository<Corretora, Long> {

    Optional<Corretora> findByCnpj(String cnpj);
    boolean existsByCnpj(String cnpj);

}
