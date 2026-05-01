package com.infra.client.brapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrapiResponseDTO {

    @JsonProperty("results")
    private List<BrapiResultDTO> results;
}
