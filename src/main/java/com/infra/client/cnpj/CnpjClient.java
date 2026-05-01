package com.infra.client.cnpj;

import com.infra.client.cnpj.dto.CnpjResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "cnpj-client",
        url = "${api.cnpj.url}"
)
public interface CnpjClient {

    @GetMapping("/cnpj/v1/{cnpj}")
    CnpjResponseDTO buscarCnpj(@PathVariable("cnpj") String cnpj);
}
