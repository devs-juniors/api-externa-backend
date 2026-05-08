package com.repositories;

import com.domains.Operacao;
import com.domains.enums.TipoOperacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperacaoRepository extends JpaRepository<Operacao, Long> {
    List<Operacao> findByCarteiraAcaoId(Long carteiraAcaoId);
    List<Operacao> findByCarteiraAcaoIdAndTipo(Long carteiraAcaoId, TipoOperacao tipo);
}
