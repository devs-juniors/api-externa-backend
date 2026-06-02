package com.infra.client.alphavantage.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlphaVantageOverviewDTO {

    @JsonProperty("Name")
    private String nomeEmpresa;
}
