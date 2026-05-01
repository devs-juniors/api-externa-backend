package com.infra.client.brapi;

import com.infra.client.brapi.dtos.BrapiResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "brapi-client",
        url = "${api.brapi.url}"
)
public interface BrapiClient {

    @GetMapping("/api/quote/{ticker}")
    BrapiResponseDTO buscarCotacao(
            @PathVariable("ticker") String ticker,
            @RequestParam("token") String token
    );
}
