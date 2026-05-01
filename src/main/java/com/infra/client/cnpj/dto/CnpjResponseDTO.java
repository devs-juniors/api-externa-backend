package com.infra.client.cnpj.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CnpjResponseDTO {

    @JsonProperty("cnpj")
    private String cnpj;

    @JsonProperty("razao_social")
    private String razaoSocial;

    @JsonProperty("nome_fantasia")
    private String nomeFantasia;

    @JsonProperty("email")
    private String email;

    @JsonProperty("ddd_telefone_1")
    private String telefone;

    @JsonProperty("cep")
    private String cep;

    @JsonProperty("logradouro")
    private String logradouro;

    @JsonProperty("numero")
    private String numero;

    @JsonProperty("complemento")
    private String complemento;

    @JsonProperty("bairro")
    private String bairro;

    @JsonProperty("municipio")
    private String cidade;

    @JsonProperty("uf")
    private String uf;

    @JsonProperty("descricao_situacao_cadastral")
    private String situacaoCadastral;

    @JsonProperty("natureza_juridica")
    private String naturezaJuridica;

    @JsonProperty("data_inicio_atividade")
    private String dataInicioAtividade;
}
