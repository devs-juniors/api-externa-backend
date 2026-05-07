package com.resources;

import com.domains.dtos.OperacaoResponseDTO;
import com.services.OperacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/operacoes")
public class OperacaoResource {

    @Autowired
    private OperacaoService operacaoService;

    @GetMapping("/carteira-acao/{carteiraAcaoId}")
    public ResponseEntity<List<OperacaoResponseDTO>> listarPorCarteiraAcao(
            @PathVariable Long carteiraAcaoId) {

        List<OperacaoResponseDTO> operacoes = operacaoService
                .listarPorCarteiraAcao(carteiraAcaoId);
        return ResponseEntity.ok(operacoes);
    }


    @GetMapping("/carteira-acao/{carteiraAcaoId}/compras")
    public ResponseEntity<List<OperacaoResponseDTO>> listarCompras(
            @PathVariable Long carteiraAcaoId) {

        List<OperacaoResponseDTO> compras = operacaoService.listarCompras(carteiraAcaoId);
        return ResponseEntity.ok(compras);
    }


    @GetMapping("/carteira-acao/{carteiraAcaoId}/vendas")
    public ResponseEntity<List<OperacaoResponseDTO>> listarVendas(
            @PathVariable Long carteiraAcaoId) {

        List<OperacaoResponseDTO> vendas = operacaoService.listarVendas(carteiraAcaoId);
        return ResponseEntity.ok(vendas);
    }
}
