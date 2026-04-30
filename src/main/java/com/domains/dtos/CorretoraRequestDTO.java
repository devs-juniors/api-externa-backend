package com.domains.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorretoraRequestDTO {

    @NotBlank(message = "CNPJ é obrigatório")
    @Pattern(
            regexp = "^\\d{2}\\.?\\d{3}\\.?\\d{3}\\/?\\d{4}-?\\d{2}$",
            message = "CNPJ em formato inválido. Use XX.XXX.XXX/XXXX-XX ou somente números"
    )
    private String cnpj;
}