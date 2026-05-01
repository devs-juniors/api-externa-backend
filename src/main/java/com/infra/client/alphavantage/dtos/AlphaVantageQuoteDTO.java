package com.infra.client.alphavantage.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlphaVantageQuoteDTO {

    @JsonProperty("01. symbol")
    private String ticker;

    @JsonProperty("05. price")
    private BigDecimal cotacaoAtual;

    @JsonProperty("07. latest trading day")
    private String dataUltimaNegociacao;
}
