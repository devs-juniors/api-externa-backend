package com.domains;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tb_carteira_acao")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
        name = "seq_carteira_acao",
        sequenceName = "seq_carteira_acao",
        allocationSize = 1
)
public class CarteiraAcao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_carteira_acao")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carteira_id", nullable = false)
    private Carteira carteira;

    @ManyToOne
    @JoinColumn(name = "acao_id", nullable = false)
    private Acao acao;

    @Column(name = "quantidade_atual", nullable = false)
    private Integer quantidadeAtual;

    @Column(name = "preco_medio_compra", nullable = false, precision = 19, scale = 2)
    private BigDecimal precoMedioCompra;

    @Column(name = "valor_total_investido", nullable = false, precision = 19, scale = 2)
    private BigDecimal valorTotalInvestido;

    @Column(name = "preco_medio_venda", precision = 19, scale = 2)
    private BigDecimal precoMedioVenda;

    @Column(name = "quantidade_vendida", nullable = false)
    private Integer quantidadeVendida = 0;

    @Column(name = "lucro_realizado", nullable = false, precision = 19, scale = 2)
    private BigDecimal lucroRealizado = BigDecimal.ZERO;

    @OneToMany(mappedBy = "carteiraAcao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Operacao> operacoes;

}
