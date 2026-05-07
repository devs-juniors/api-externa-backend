package com.mappers;

import com.domains.CarteiraAcao;
import com.domains.dtos.CarteiraAcaoResponseDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class CarteiraAcaoMapper {

    public CarteiraAcaoResponseDTO toResponseDTO(CarteiraAcao carteiraAcao) {
        CarteiraAcaoResponseDTO dto = new CarteiraAcaoResponseDTO();
        dto.setId(carteiraAcao.getId());
        dto.setTicker(carteiraAcao.getAcao().getTicker());
        dto.setNomeEmpresa(carteiraAcao.getAcao().getNomeEmpresa());
        dto.setMercado(carteiraAcao.getAcao().getMercado());
        dto.setMoeda(carteiraAcao.getAcao().getMoeda());
        dto.setQuantidadeAtual(carteiraAcao.getQuantidadeAtual());
        dto.setPrecoMedioCompra(carteiraAcao.getPrecoMedioCompra());
        dto.setValorTotalInvestido(carteiraAcao.getValorTotalInvestido());
        dto.setCotacaoAtual(carteiraAcao.getAcao().getCotacaoAtual());


        BigDecimal lucro = carteiraAcao.getAcao().getCotacaoAtual()
                .subtract(carteiraAcao.getPrecoMedioCompra())
                .multiply(new BigDecimal(carteiraAcao.getQuantidadeAtual()))
                .setScale(2, RoundingMode.HALF_UP);
        dto.setLucroOuPrejuizo(lucro);


        if (carteiraAcao.getValorTotalInvestido().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal percentual = lucro
                    .divide(carteiraAcao.getValorTotalInvestido(), 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"))
                    .setScale(2, RoundingMode.HALF_UP);
            dto.setPercentualVariacao(percentual);
        }

        return dto;
    }
}
