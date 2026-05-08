package com.domains;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_carteira")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
        name = "seq_carteira",
        sequenceName = "seq_carteira",
        allocationSize = 1
)
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_carteira")
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "corretora_id", nullable = false)
    private Corretora corretora;

    @OneToMany(mappedBy = "carteira", cascade = CascadeType.ALL)
    private List<CarteiraAcao> posicoes;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
    }
}
