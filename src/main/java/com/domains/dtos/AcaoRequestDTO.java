package com.domains.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcaoRequestDTO {

    @NotBlank(message = "Ticker é obrigatório")
    private String ticker;

    @NotBlank(message = "Mercado é obrigatório")
    @Pattern(
            regexp = "^(BR|EUA)$",
            message = "Mercado deve ser BR ou EUA"
    )
    private String mercado;
}
