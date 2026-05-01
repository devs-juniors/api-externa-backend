package com.infra.adapter;

import com.domains.dtos.AcaoResponseDTO;
import com.infra.client.alphavantage.AlphaVantageClient;
import com.infra.client.alphavantage.dtos.AlphaVantageQuoteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AlphaVantageAdapter implements CotacaoAdapter {

    @Autowired
    private AlphaVantageClient alphaVantageClient;


    @Value("${api.alphavantage.key}")
    private String apiKey;

    @Override
    public boolean suporta(String mercado) {
        return "EUA".equalsIgnoreCase(mercado);
    }

    @Override
    public AcaoResponseDTO buscar(String ticker) {

        AlphaVantageQuoteDTO quote = alphaVantageClient
                .buscarCotacao("GLOBAL_QUOTE", ticker, apiKey)
                .getGlobalQuote();

        AcaoResponseDTO dto = new AcaoResponseDTO();
        dto.setTicker(quote.getTicker());
        dto.setNomeEmpresa(null);
        dto.setMercado("EUA");
        dto.setMoeda("USD");
        dto.setCotacaoAtual(quote.getCotacaoAtual());
        dto.setDataHoraCotacao(LocalDateTime.now());
        return dto;
    }
}
