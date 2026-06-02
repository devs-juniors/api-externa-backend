package com.infra.facade;

import com.infra.client.cvm.CvmCorretoraClient;
import com.infra.client.cvm.dto.CvmCorretoraResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CvmFacade {

    @Autowired
    private CvmCorretoraClient cvmCorretoraClient;

    public boolean isValidadaNaCvm(String cnpj) {
        try {
            CvmCorretoraResponseDTO response = cvmCorretoraClient.buscarCorretora(cnpj);
            return "EM FUNCIONAMENTO NORMAL".equals(response.getStatus());
        } catch (RuntimeException e) {
            return false;
        }
    }
}
