package com.infra.client.cep;

import com.infra.client.cep.dto.CepResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "CepClient",
        url = "${api.cep.url}"
)
public interface CepClient {

    @GetMapping("/ws/{cep}/json/")
    CepResponseDTO buscarCep(@PathVariable("cep") String cep);
}
