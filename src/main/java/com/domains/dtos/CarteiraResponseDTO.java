package com.domains.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarteiraResponseDTO {

    private Long id;
    private String nome;
    private String nomeCorretora;
    private LocalDateTime dataCriacao;
    private List<CarteiraAcaoResponseDTO> posicoes;
}
