package com.domains;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_corretora")
@NoArgsConstructor
@AllArgsConstructor
@Data
@SequenceGenerator(
        name = "seq_corretora",
        sequenceName = "seq_corretora",
        allocationSize = 1
)
public class Corretora {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_corretora")
    private Long id;

    @Column(nullable = false, unique = true, length = 14)
    private String cnpj;

    @Column(name = "razao_social", nullable = false, length = 100)
    private String razaoSocial;

    @Column(name = "nome_fantasia", length = 100)
    private String nomeFantasia;        // pode vir null da API

    @Column(length = 100)
    private String email;

    @Column(length = 30)
    private String telefone;
    @Column(length = 8)
    private String cep;

    @Column(length = 100)
    private String logradouro;

    @Column(length = 10)
    private String numero;

    @Column(length = 100)
    private String complemento;

    @Column(length = 50)
    private String bairro;

    @Column(length = 50)
    private String cidade;

    @Column(length = 2)
    private String uf;

    @Column(name = "situacao_cadastral", length = 30)
    private String situacaoCadastral;

    @Column(name = "validada_na_cvm", nullable = false)
    private Boolean validadaNaCvm = false;

    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @PrePersist
    public void prePersist() {
        this.dataCadastro = LocalDateTime.now();
    }
}