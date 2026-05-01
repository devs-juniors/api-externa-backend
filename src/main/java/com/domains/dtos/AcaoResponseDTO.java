package com.domains.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcaoResponseDTO {

    private Long id;
    private String ticker;
    private String nomeEmpresa;
    private String mercado;
    private String moeda;
    private BigDecimal cotacaoAtual;
    private LocalDateTime dataHoraCotacao;
}