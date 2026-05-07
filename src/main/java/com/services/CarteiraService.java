package com.services;

import com.domains.*;
import com.domains.dtos.CarteiraRequestDTO;
import com.domains.dtos.CarteiraResponseDTO;
import com.domains.dtos.OperacaoRequestDTO;
import com.domains.dtos.OperacaoResponseDTO;
import com.domains.enums.TipoOperacao;
import com.mappers.CarteiraMapper;
import com.mappers.OperacaoMapper;
import com.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CarteiraService {

    @Autowired
    private CarteiraRepository carteiraRepository;

    @Autowired
    private CarteiraAcaoRepository carteiraAcaoRepository;

    @Autowired
    private OperacaoRepository operacaoRepository;

    @Autowired
    private CorretoraRepository corretoraRepository;

    @Autowired
    private AcaoRepository acaoRepository;

    @Autowired
    private CarteiraMapper carteiraMapper;

    @Autowired
    private OperacaoMapper operacaoMapper;

    public CarteiraResponseDTO cadastrar(CarteiraRequestDTO request) {


        Corretora corretora = corretoraRepository.findById(request.getCorretoraId())
                .orElseThrow(() -> new RuntimeException("Corretora não encontrada"));


        if (carteiraRepository.existsByNomeAndCorretoraId(
                request.getNome(), request.getCorretoraId())) {
            throw new RuntimeException("Já existe uma carteira com esse nome nessa corretora");
        }

        Carteira carteira = new Carteira();
        carteira.setNome(request.getNome());
        carteira.setCorretora(corretora);

        return carteiraMapper.toResponseDTO(carteiraRepository.save(carteira));
    }

    public List<CarteiraResponseDTO> listar() {
        return carteiraRepository.findAll()
                .stream()
                .map(carteiraMapper::toResponseDTO)
                .toList();
    }

    public CarteiraResponseDTO buscarPorId(Long id) {
        Carteira carteira = carteiraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carteira não encontrada"));
        return carteiraMapper.toResponseDTO(carteira);
    }


    @Transactional
    public OperacaoResponseDTO comprar(Long carteiraId, OperacaoRequestDTO request) {


        Carteira carteira = carteiraRepository.findById(carteiraId)
                .orElseThrow(() -> new RuntimeException("Carteira não encontrada"));


        Acao acao = acaoRepository.findByTicker(request.getTicker().toUpperCase())
                .orElseThrow(() -> new RuntimeException(
                        "Ação não encontrada — cadastre a ação antes de comprar"));

        BigDecimal valorTotal = request.getPrecoUnitario()
                .multiply(new BigDecimal(request.getQuantidade()))
                .setScale(2, RoundingMode.HALF_UP);

        CarteiraAcao carteiraAcao = carteiraAcaoRepository
                .findByCarteiraIdAndAcaoId(carteiraId, acao.getId())
                .orElse(null);

        if (carteiraAcao == null) {

            carteiraAcao = new CarteiraAcao();
            carteiraAcao.setCarteira(carteira);
            carteiraAcao.setAcao(acao);
            carteiraAcao.setQuantidadeAtual(request.getQuantidade());
            carteiraAcao.setValorTotalInvestido(valorTotal);
            carteiraAcao.setPrecoMedioCompra(
                    request.getPrecoUnitario().setScale(2, RoundingMode.HALF_UP)
            );
        } else {

            BigDecimal novoTotalInvestido = carteiraAcao.getValorTotalInvestido().add(valorTotal);
            Integer novaQuantidade = carteiraAcao.getQuantidadeAtual() + request.getQuantidade();

            BigDecimal novoPrecoMedio = novoTotalInvestido
                    .divide(new BigDecimal(novaQuantidade), 2, RoundingMode.HALF_UP);

            carteiraAcao.setQuantidadeAtual(novaQuantidade);
            carteiraAcao.setValorTotalInvestido(novoTotalInvestido);
            carteiraAcao.setPrecoMedioCompra(novoPrecoMedio);
        }

        carteiraAcaoRepository.save(carteiraAcao);

        Operacao operacao = new Operacao();
        operacao.setCarteiraAcao(carteiraAcao);
        operacao.setTipo(TipoOperacao.COMPRA);
        operacao.setQuantidade(request.getQuantidade());
        operacao.setPrecoUnitario(request.getPrecoUnitario().setScale(2, RoundingMode.HALF_UP));
        operacao.setValorTotal(valorTotal);

        return operacaoMapper.toResponseDTO(operacaoRepository.save(operacao));
    }


    @Transactional
    public OperacaoResponseDTO vender(Long carteiraId, OperacaoRequestDTO request) {

        Acao acao = acaoRepository.findByTicker(request.getTicker().toUpperCase())
                .orElseThrow(() -> new RuntimeException("Ação não encontrada"));

        CarteiraAcao carteiraAcao = carteiraAcaoRepository
                .findByCarteiraIdAndAcaoId(carteiraId, acao.getId())
                .orElseThrow(() -> new RuntimeException(
                        "Você não possui essa ação nessa carteira"));

        if (request.getQuantidade() > carteiraAcao.getQuantidadeAtual()) {
            throw new RuntimeException(
                    "Quantidade insuficiente. Você possui " +
                            carteiraAcao.getQuantidadeAtual() + " ações de " +
                            request.getTicker().toUpperCase()
            );
        }

        BigDecimal valorTotal = request.getPrecoUnitario()
                .multiply(new BigDecimal(request.getQuantidade()))
                .setScale(2, RoundingMode.HALF_UP);

        Integer novaQuantidade = carteiraAcao.getQuantidadeAtual() - request.getQuantidade();
        carteiraAcao.setQuantidadeAtual(novaQuantidade);

        BigDecimal novoTotalInvestido = carteiraAcao.getPrecoMedioCompra()
                .multiply(new BigDecimal(novaQuantidade))
                .setScale(2, RoundingMode.HALF_UP);
        carteiraAcao.setValorTotalInvestido(novoTotalInvestido);

        // preço médio não muda na venda
        carteiraAcaoRepository.save(carteiraAcao);

        // registra a operação de venda
        Operacao operacao = new Operacao();
        operacao.setCarteiraAcao(carteiraAcao);
        operacao.setTipo(TipoOperacao.VENDA);
        operacao.setQuantidade(request.getQuantidade());
        operacao.setPrecoUnitario(request.getPrecoUnitario().setScale(2, RoundingMode.HALF_UP));
        operacao.setValorTotal(valorTotal);

        return operacaoMapper.toResponseDTO(operacaoRepository.save(operacao));
    }

    public List<OperacaoResponseDTO> listarOperacoes(Long carteiraId) {
        Carteira carteira = carteiraRepository.findById(carteiraId)
                .orElseThrow(() -> new RuntimeException("Carteira não encontrada"));

        return carteira.getPosicoes().stream()
                .flatMap(posicao -> operacaoRepository
                        .findByCarteiraAcaoId(posicao.getId())
                        .stream())
                .map(operacaoMapper::toResponseDTO)
                .toList();
    }
}
