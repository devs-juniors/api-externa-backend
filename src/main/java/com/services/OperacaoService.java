package com.services;

import com.domains.dtos.OperacaoResponseDTO;
import com.domains.enums.TipoOperacao;
import com.mappers.OperacaoMapper;
import com.repositories.OperacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperacaoService {

    @Autowired
    private OperacaoRepository operacaoRepository;

    @Autowired
    private OperacaoMapper operacaoMapper;

    public List<OperacaoResponseDTO> listarPorCarteiraAcao(Long carteiraAcaoId) {
        return operacaoRepository.findByCarteiraAcaoId(carteiraAcaoId)
                .stream()
                .map(operacaoMapper::toResponseDTO)
                .toList();
    }

    public List<OperacaoResponseDTO> listarCompras(Long carteiraAcaoId) {
        return operacaoRepository
                .findByCarteiraAcaoIdAndTipo(carteiraAcaoId, TipoOperacao.COMPRA)
                .stream()
                .map(operacaoMapper::toResponseDTO)
                .toList();
    }

    public List<OperacaoResponseDTO> listarVendas(Long carteiraAcaoId) {
        return operacaoRepository
                .findByCarteiraAcaoIdAndTipo(carteiraAcaoId, TipoOperacao.VENDA)
                .stream()
                .map(operacaoMapper::toResponseDTO)
                .toList();
    }

}
