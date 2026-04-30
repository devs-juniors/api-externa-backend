package com.mappers;

import org.springframework.stereotype.Component;

@Component
public class AcaoMapper {

    public AcaoResponseDTO toResponseDTO(Acao acao) {
        AcaoResponseDTO dto = new AcaoResponseDTO();
        dto.setId(acao.getId());
        dto.setTicker(acao.getTicker());
        dto.setNomeEmpresa(acao.getNomeEmpresa());
        dto.setMercado(acao.getMercado());
        dto.setMoeda(acao.getMoeda());
        dto.setCotacaoAtual(acao.getCotacaoAtual());
        dto.setDataHoraCotacao(acao.getDataHoraCotacao());
        return dto;
    }

    public Acao toEntity(AcaoResponseDTO dto) {
        Acao acao = new Acao();
        acao.setId(dto.getId());
        acao.setTicker(dto.getTicker());
        acao.setNomeEmpresa(dto.getNomeEmpresa());
        acao.setMercado(dto.getMercado());
        acao.setMoeda(dto.getMoeda());
        acao.setCotacaoAtual(dto.getCotacaoAtual());
        acao.setDataHoraCotacao(dto.getDataHoraCotacao());
        return acao;
    }
}
