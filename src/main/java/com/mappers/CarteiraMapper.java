package com.mappers;

import com.domains.Carteira;
import com.domains.dtos.CarteiraAcaoResponseDTO;
import com.domains.dtos.CarteiraResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarteiraMapper {

    @Autowired
    private CarteiraAcaoMapper carteiraAcaoMapper;

    public CarteiraResponseDTO toResponseDTO(Carteira carteira) {
        CarteiraResponseDTO dto = new CarteiraResponseDTO();
        dto.setId(carteira.getId());
        dto.setNome(carteira.getNome());
        dto.setNomeCorretora(carteira.getCorretora().getRazaoSocial());
        dto.setDataCriacao(carteira.getDataCriacao());

        // mapeia as posições se existirem
        if (carteira.getPosicoes() != null) {
            List<CarteiraAcaoResponseDTO> posicoes = carteira.getPosicoes()
                    .stream()
                    .map(carteiraAcaoMapper::toResponseDTO)
                    .toList();
            dto.setPosicoes(posicoes);
        }

        return dto;
    }
}
