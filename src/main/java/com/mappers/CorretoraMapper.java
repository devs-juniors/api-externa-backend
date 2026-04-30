package com.mappers;

import org.springframework.stereotype.Component;

@Component
public class CorretoraMapper {

    public CorretoraResponseDTO toResponseDTO(Corretora corretora) {
        CorretoraResponseDTO dto = new CorretoraResponseDTO();
        dto.setId(corretora.getId());
        dto.setCnpj(corretora.getCnpj());
        dto.setRazaoSocial(corretora.getRazaoSocial());
        dto.setNomeFantasia(corretora.getNomeFantasia());
        dto.setEmail(corretora.getEmail());
        dto.setTelefone(corretora.getTelefone());
        dto.setCep(corretora.getCep());
        dto.setLogradouro(corretora.getLogradouro());
        dto.setNumero(corretora.getNumero());
        dto.setComplemento(corretora.getComplemento());
        dto.setBairro(corretora.getBairro());
        dto.setCidade(corretora.getCidade());
        dto.setUf(corretora.getUf());
        dto.setSituacaoCadastral(corretora.getSituacaoCadastral());
        dto.setValidadaNaCvm(corretora.getValidadaNaCvm());
        dto.setDataCadastro(corretora.getDataCadastro());
        return dto;
    }

    public Corretora toEntity(CorretoraResponseDTO dto) {
        Corretora corretora = new Corretora();
        corretora.setId(dto.getId());
        corretora.setCnpj(dto.getCnpj());
        corretora.setRazaoSocial(dto.getRazaoSocial());
        corretora.setNomeFantasia(dto.getNomeFantasia());
        corretora.setEmail(dto.getEmail());
        corretora.setTelefone(dto.getTelefone());
        corretora.setCep(dto.getCep());
        corretora.setLogradouro(dto.getLogradouro());
        corretora.setNumero(dto.getNumero());
        corretora.setComplemento(dto.getComplemento());
        corretora.setBairro(dto.getBairro());
        corretora.setCidade(dto.getCidade());
        corretora.setUf(dto.getUf());
        corretora.setSituacaoCadastral(dto.getSituacaoCadastral());
        corretora.setValidadaNaCvm(dto.getValidadaNaCvm());
        return corretora;
    }
}
