package com.domains;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_acao")
@NoArgsConstructor
@AllArgsConstructor
@Data
@SequenceGenerator(
        name = "seq_acao",
        sequenceName = "seq_acao",
        allocationSize = 1
)
public class Acao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_acao")
    private Long id;

    @Column(nullable = false, unique = true)
    private String ticker;

    @Column(name = "nome_empresa")
    private String nomeEmpresa;

    @Column(nullable = false)
    private String mercado;

    @Column(nullable = false)
    private String moeda;

    @Column(name = "cotacao_atual", precision = 19, scale = 4)
    private BigDecimal cotacaoAtual;

    @Column(name = "data_hora_cotacao")
    private LocalDateTime dataHoraCotacao;

}

