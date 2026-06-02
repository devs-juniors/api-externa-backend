package com.domains;

import com.domains.enums.TipoOperacao;
import com.infra.converters.TipoOperacaoConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_operacao")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
        name = "seq_operacao",
        sequenceName = "seq_operacao",
        allocationSize = 1
)
public class Operacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_operacao")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carteira_acao_id", nullable = false)
    private CarteiraAcao carteiraAcao;

    @Convert(converter = TipoOperacaoConverter.class)
    @Column(nullable = false)
    private TipoOperacao tipo;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(name = "preco_unitario", nullable = false, precision = 19, scale = 2)
    private BigDecimal precoUnitario;

    @Column(name = "valor_total", nullable = false, precision = 19, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "data_operacao", nullable = false, updatable = false)
    private LocalDateTime dataOperacao;

    @PrePersist
    public void prePersist() {
        this.dataOperacao = LocalDateTime.now();

    }
}