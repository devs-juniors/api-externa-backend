package com.mappers;

import com.domains.Operacao;
import com.domains.dtos.OperacaoResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class OperacaoMapper {

    public OperacaoResponseDTO toResponseDTO(Operacao operacao) {
        OperacaoResponseDTO dto = new OperacaoResponseDTO();
        dto.setId(operacao.getId());
        dto.setTicker(operacao.getCarteiraAcao().getAcao().getTicker());
        dto.setTipo(operacao.getTipo().getDescricao());
        dto.setQuantidade(operacao.getQuantidade());
        dto.setPrecoUnitario(operacao.getPrecoUnitario());
        dto.setValorTotal(operacao.getValorTotal());
        dto.setDataOperacao(operacao.getDataOperacao());
        return dto;
    }
}
