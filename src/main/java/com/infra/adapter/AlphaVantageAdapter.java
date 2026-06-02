package com.infra.adapter;

import com.domains.dtos.AcaoResponseDTO;
import com.infra.client.alphavantage.AlphaVantageClient;
import com.infra.client.alphavantage.dtos.AlphaVantageQuoteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

        if (quote == null || quote.getCotacaoAtual() == null) {
            throw new RuntimeException(
                    "Ticker '" + ticker + "' não encontrado no mercado americano. " +
                    "Verifique o ticker ou tente novamente mais tarde.");
        }

        String nomeEmpresa = null;
        try {
            nomeEmpresa = alphaVantageClient
                    .buscarOverview("OVERVIEW", ticker, apiKey)
                    .getNomeEmpresa();
        } catch (Exception ignored) {}

        AcaoResponseDTO dto = new AcaoResponseDTO();
        dto.setTicker(quote.getTicker());
        dto.setNomeEmpresa(nomeEmpresa);
        dto.setMercado("EUA");
        dto.setMoeda("USD");
        dto.setCotacaoAtual(quote.getCotacaoAtual().setScale(2, RoundingMode.HALF_UP));
        dto.setDataHoraCotacao(LocalDateTime.now());
        return dto;
    }
}