package com.domains.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorretoraResponseDTO {

    private Long id;
    private String cnpj;
    private String razaoSocial;
    private String nomeFantasia;
    private String email;
    private String telefone;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String situacaoCadastral;
    private Boolean validadaNaCvm;
    private LocalDateTime dataCadastro;
}