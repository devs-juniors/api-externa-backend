package com.infra.adapter;

import com.domains.dtos.AcaoResponseDTO;
import com.infra.client.brapi.BrapiClient;
import com.infra.client.brapi.dtos.BrapiResultDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BrapiAdapter implements CotacaoAdapter{

    @Autowired
    private BrapiClient brapiClient;

    @Value("${api.brapi.token}")
    private String token;

    @Override
    public boolean suporta(String mercado) {
        return "BR".equalsIgnoreCase(mercado);
    }

    @Override
    public AcaoResponseDTO buscar(String ticker) {


        BrapiResultDTO resultado = brapiClient
                .buscarCotacao(ticker, token)
                .getResults()
                .get(0);

        if (resultado.getCotacaoAtual() == null) {
            throw new RuntimeException(
                    "Ticker '" + ticker + "' não encontrado no mercado brasileiro. " +
                    "Verifique o ticker informado.");
        }

        if (resultado.getMoeda() != null && !"BRL".equalsIgnoreCase(resultado.getMoeda())) {
            throw new RuntimeException(
                    "Ticker '" + ticker + "' não pertence ao mercado brasileiro " +
                    "(moeda retornada: " + resultado.getMoeda() + "). " +
                    "Para ações americanas, selecione o mercado EUA.");
        }

        AcaoResponseDTO dto = new AcaoResponseDTO();
        dto.setTicker(resultado.getTicker());
        dto.setNomeEmpresa(resultado.getNomeEmpresa());
        dto.setMercado("BR");
        dto.setMoeda("BRL");
        dto.setCotacaoAtual(resultado.getCotacaoAtual());
        dto.setDataHoraCotacao(LocalDateTime.now());
        return dto;
    }
}
