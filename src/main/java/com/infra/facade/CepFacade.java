package com.infra.facade;

import com.infra.client.cep.CepClient;
import com.infra.client.cep.dto.CepResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CepFacade {

    @Autowired
    private CepClient cepClient;

    public CepResponseDTO buscar(String cep) {

        String cepLimpo = cep.replaceAll("-", "");

        if (cepLimpo.length() != 8) {
            throw new RuntimeException("CEP inválido: deve conter 8 números");
        }

        CepResponseDTO resposta = cepClient.buscarCep(cepLimpo);

        if (resposta.getCep() != null) {
            resposta.setCep(resposta.getCep().replaceAll("-", ""));
        }

        return resposta;
    }
}