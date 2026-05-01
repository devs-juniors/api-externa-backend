package com.infra.client.brapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrapiResultDTO {

    @JsonProperty("symbol")
    private String ticker;

    @JsonProperty("longName")
    private String nomeEmpresa;

    @JsonProperty("regularMarketPrice")
    private BigDecimal cotacaoAtual;

    @JsonProperty("currency")
    private String moeda;

    @JsonProperty("regularMarketTime")
    private String dataHoraCotacao;
}
