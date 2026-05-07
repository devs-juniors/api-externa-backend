package com.domains.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperacaoRequestDTO {

    @NotBlank(message = "Ticker é obrigatório")
    private String ticker;

    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser maior que zero")
    private Integer quantidade;

    @NotNull(message = "Preço unitário é obrigatório")
    @Positive(message = "Preço deve ser maior que zero")
    private BigDecimal precoUnitario;
}
