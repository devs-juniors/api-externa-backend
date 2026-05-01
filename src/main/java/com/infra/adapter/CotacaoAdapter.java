package com.infra.adapter;

import com.domains.dtos.AcaoResponseDTO;

public interface CotacaoAdapter {

    boolean suporta(String mercado);

    AcaoResponseDTO buscar(String ticker);

}
