package com.domains.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarteiraAcaoResponseDTO {

    private Long id;
    private String ticker;
    private String nomeEmpresa;
    private String mercado;
    private String moeda;
    private Integer quantidadeAtual;
    private BigDecimal precoMedioCompra;
    private BigDecimal precoMedioVenda;
    private Integer quantidadeVendida;
    private BigDecimal lucroMedioPorAcao;
    private BigDecimal lucroRealizado;
    private BigDecimal valorTotalInvestido;
    private BigDecimal cotacaoAtual;
    private BigDecimal lucroOuPrejuizo;
    private BigDecimal percentualVariacao;
}
