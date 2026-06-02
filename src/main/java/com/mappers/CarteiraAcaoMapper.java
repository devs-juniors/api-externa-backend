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
        dto.setPrecoMedioVenda(carteiraAcao.getPrecoMedioVenda());
        dto.setQuantidadeVendida(carteiraAcao.getQuantidadeVendida());
        if (carteiraAcao.getQuantidadeVendida() != null && carteiraAcao.getQuantidadeVendida() > 0
                && carteiraAcao.getPrecoMedioVenda() != null) {
            dto.setLucroMedioPorAcao(carteiraAcao.getPrecoMedioVenda()
                    .subtract(carteiraAcao.getPrecoMedioCompra())
                    .setScale(2, RoundingMode.HALF_UP));
        }
        BigDecimal realizado = carteiraAcao.getLucroRealizado() != null
                ? carteiraAcao.getLucroRealizado() : BigDecimal.ZERO;
        dto.setLucroRealizado(realizado);
        dto.setValorTotalInvestido(carteiraAcao.getValorTotalInvestido());
        dto.setCotacaoAtual(carteiraAcao.getAcao().getCotacaoAtual());

        BigDecimal naoRealizado = carteiraAcao.getQuantidadeAtual() > 0
                ? carteiraAcao.getAcao().getCotacaoAtual()
                        .subtract(carteiraAcao.getPrecoMedioCompra())
                        .multiply(new BigDecimal(carteiraAcao.getQuantidadeAtual()))
                        .setScale(2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        BigDecimal lucroTotal = naoRealizado.add(realizado).setScale(2, RoundingMode.HALF_UP);
        dto.setLucroOuPrejuizo(lucroTotal);

        if (carteiraAcao.getValorTotalInvestido().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal percentual = lucroTotal
                    .divide(carteiraAcao.getValorTotalInvestido(), 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"))
                    .setScale(2, RoundingMode.HALF_UP);
            dto.setPercentualVariacao(percentual);
        }

        return dto;
    }
}
