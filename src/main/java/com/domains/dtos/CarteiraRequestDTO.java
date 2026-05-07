package com.domains.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarteiraRequestDTO {

    @NotBlank(message = "Nome da carteira é obrigatório")
    private String nome;

    @NotNull(message = "Id da corretora é obrigatório")
    private Long corretoraId;
}