package com.services;

import com.domains.Acao;
import com.domains.Carteira;
import com.domains.CarteiraAcao;
import com.domains.dtos.AcaoResponseDTO;
import com.domains.dtos.OperacaoRequestDTO;
import com.domains.dtos.OperacaoResponseDTO;
import com.mappers.OperacaoMapper;
import com.repositories.AcaoRepository;
import com.repositories.CarteiraAcaoRepository;
import com.repositories.CarteiraRepository;
import com.repositories.OperacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarteiraServiceTest {

    @Mock private CarteiraRepository carteiraRepository;
    @Mock private CarteiraAcaoRepository carteiraAcaoRepository;
    @Mock private OperacaoRepository operacaoRepository;
    @Mock private AcaoRepository acaoRepository;
    @Mock private AcaoService acaoService;
    @Mock private OperacaoMapper operacaoMapper;

    @InjectMocks
    private CarteiraService carteiraService;

    @Test
    void deveUsarCotacaoAtualDaApiNaCompra() {
        Long carteiraId = 1L;
        OperacaoRequestDTO request = new OperacaoRequestDTO();
        request.setTicker("PETR4");
        request.setQuantidade(10);

        Carteira carteira = new Carteira();
        carteira.setId(carteiraId);

        Acao acao = acaoMock(1L, "PETR4", new BigDecimal("30.00"));

        AcaoResponseDTO cotacaoFresca = new AcaoResponseDTO();
        cotacaoFresca.setCotacaoAtual(new BigDecimal("38.50"));

        when(carteiraRepository.findById(carteiraId)).thenReturn(Optional.of(carteira));
        when(acaoRepository.findByTicker("PETR4")).thenReturn(Optional.of(acao));
        when(acaoService.atualizarCotacao(1L)).thenReturn(cotacaoFresca);
        when(carteiraAcaoRepository.findByCarteiraIdAndAcaoId(carteiraId, 1L)).thenReturn(Optional.empty());
        when(carteiraAcaoRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        ArgumentCaptor<com.domains.Operacao> captor = ArgumentCaptor.forClass(com.domains.Operacao.class);
        when(operacaoRepository.save(captor.capture())).thenAnswer(i -> i.getArgument(0));
        when(operacaoMapper.toResponseDTO(any())).thenReturn(new OperacaoResponseDTO());

        carteiraService.comprar(carteiraId, request);

        assertThat(captor.getValue().getPrecoUnitario())
                .isEqualByComparingTo(new BigDecimal("38.50"));
    }

    @Test
    void deveUsarCotacaoAtualDaApiNaVenda() {
        Long carteiraId = 1L;
        OperacaoRequestDTO request = new OperacaoRequestDTO();
        request.setTicker("PETR4");
        request.setQuantidade(5);

        Acao acao = acaoMock(1L, "PETR4", new BigDecimal("30.00"));

        CarteiraAcao posicao = new CarteiraAcao();
        posicao.setAcao(acao);
        posicao.setQuantidadeAtual(10);
        posicao.setPrecoMedioCompra(new BigDecimal("30.00"));
        posicao.setValorTotalInvestido(new BigDecimal("300.00"));
        posicao.setQuantidadeVendida(0);
        posicao.setLucroRealizado(BigDecimal.ZERO);

        AcaoResponseDTO cotacaoFresca = new AcaoResponseDTO();
        cotacaoFresca.setCotacaoAtual(new BigDecimal("42.00"));

        when(acaoRepository.findByTicker("PETR4")).thenReturn(Optional.of(acao));
        when(carteiraAcaoRepository.findByCarteiraIdAndAcaoId(carteiraId, 1L)).thenReturn(Optional.of(posicao));
        when(acaoService.atualizarCotacao(1L)).thenReturn(cotacaoFresca);
        when(carteiraAcaoRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        ArgumentCaptor<com.domains.Operacao> captor = ArgumentCaptor.forClass(com.domains.Operacao.class);
        when(operacaoRepository.save(captor.capture())).thenAnswer(i -> i.getArgument(0));
        when(operacaoMapper.toResponseDTO(any())).thenReturn(new OperacaoResponseDTO());

        carteiraService.vender(carteiraId, request);

        assertThat(captor.getValue().getPrecoUnitario())
                .isEqualByComparingTo(new BigDecimal("42.00"));
    }

    @Test
    void deveLancarExcecaoQuandoCotacaoNaoDisponivelNaCompra() {
        Long carteiraId = 1L;
        OperacaoRequestDTO request = new OperacaoRequestDTO();
        request.setTicker("PETR4");
        request.setQuantidade(10);

        Carteira carteira = new Carteira();
        carteira.setId(carteiraId);

        Acao acao = acaoMock(1L, "PETR4", null);

        AcaoResponseDTO cotacaoSemPreco = new AcaoResponseDTO();
        cotacaoSemPreco.setCotacaoAtual(null);

        when(carteiraRepository.findById(carteiraId)).thenReturn(Optional.of(carteira));
        when(acaoRepository.findByTicker("PETR4")).thenReturn(Optional.of(acao));
        when(acaoService.atualizarCotacao(1L)).thenReturn(cotacaoSemPreco);

        assertThatThrownBy(() -> carteiraService.comprar(carteiraId, request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Cotação não disponível");
    }

    private Acao acaoMock(Long id, String ticker, BigDecimal cotacao) {
        Acao acao = new Acao();
        acao.setId(id);
        acao.setTicker(ticker);
        acao.setCotacaoAtual(cotacao);
        acao.setMercado("BR");
        acao.setMoeda("BRL");
        return acao;
    }
}
