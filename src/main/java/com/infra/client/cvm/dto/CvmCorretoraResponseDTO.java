package com.infra.client.cvm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CvmCorretoraResponseDTO {

    @JsonProperty("cnpj")
    private String cnpj;

    @JsonProperty("nome_social")
    private String nomeSocial;

    @JsonProperty("nome_comercial")
    private String nomeComercial;

    @JsonProperty("tipo")
    private String tipo;

    @JsonProperty("codigo_cvm")
    private String codigoCvm;

    @JsonProperty("status")
    private String status;

    @JsonProperty("data_inicio_situacao")
    private String dataInicioSituacao;

    @JsonProperty("data_registro")
    private String dataRegistro;

    @JsonProperty("email")
    private String email;

    @JsonProperty("telefone")
    private String telefone;

    @JsonProperty("logradouro")
    private String logradouro;

    @JsonProperty("complemento")
    private String complemento;

    @JsonProperty("bairro")
    private String bairro;

    @JsonProperty("municipio")
    private String municipio;

    @JsonProperty("uf")
    private String uf;

    @JsonProperty("cep")
    private String cep;

    @JsonProperty("pais")
    private String pais;
}
