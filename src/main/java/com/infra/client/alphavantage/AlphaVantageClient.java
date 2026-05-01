package com.infra.client.alphavantage;

import com.infra.client.alphavantage.dtos.AlphaVantageResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "alphavantage-client",
        url = "${api.alphavantage.url}"
)
public interface AlphaVantageClient {

    @GetMapping("/query")
    AlphaVantageResponseDTO buscarCotacao(
            @RequestParam("function") String function,
            @RequestParam("symbol") String symbol,
            @RequestParam("apikey") String apiKey
    );
}
