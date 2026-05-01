package com.infra.facade;

import com.infra.client.cnpj.CnpjClient;
import com.infra.client.cnpj.dto.CnpjResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CnpjFacade {

    @Autowired
    private CnpjClient cnpjClient;

    public CnpjResponseDTO buscar(String cnpj) {

        String cnpjLimpo = cnpj.replaceAll("[.\\-/]", "");

        if (cnpjLimpo.length() != 14) {
            throw new RuntimeException("CNPJ inválido: deve conter 14 dígitos");
        }

        if (!cnpjLimpo.matches("\\d+")) {
            throw new RuntimeException("CNPJ inválido: deve conter apenas números");
        }

        if (!cnpjValido(cnpjLimpo)) {
            throw new RuntimeException("CNPJ inválido: dígitos verificadores incorretos");
        }

        return cnpjClient.buscarCnpj(cnpjLimpo);
    }

    private boolean cnpjValido(String cnpj) {


        if (cnpj.chars().distinct().count() == 1) return false;

        int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};


        int soma = 0;
        for (int i = 0; i < 12; i++)
            soma += Character.getNumericValue(cnpj.charAt(i)) * pesos1[i];
        int dig1 = soma % 11 < 2 ? 0 : 11 - (soma % 11);


        soma = 0;
        for (int i = 0; i < 13; i++)
            soma += Character.getNumericValue(cnpj.charAt(i)) * pesos2[i];
        int dig2 = soma % 11 < 2 ? 0 : 11 - (soma % 11);

        return dig1 == Character.getNumericValue(cnpj.charAt(12)) &&
                dig2 == Character.getNumericValue(cnpj.charAt(13));
    }
}
