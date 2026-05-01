package com.infra.client.alphavantage.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlphaVantageResponseDTO {


    @JsonProperty("Global Quote")
    private AlphaVantageQuoteDTO globalQuote;
}
