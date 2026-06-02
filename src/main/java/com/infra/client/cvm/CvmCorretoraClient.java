package com.infra.client.cvm;

import com.infra.client.cvm.dto.CvmCorretoraResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "cvm-corretora-client",
        url = "${api.cnpj.url}"
)
public interface CvmCorretoraClient {

    @GetMapping("/cvm/corretoras/v1/{cnpj}")
    CvmCorretoraResponseDTO buscarCorretora(@PathVariable("cnpj") String cnpj);
}
