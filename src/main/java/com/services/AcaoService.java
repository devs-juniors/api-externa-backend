package com.services;

import com.domains.Acao;
import com.domains.dtos.AcaoRequestDTO;
import com.domains.dtos.AcaoResponseDTO;
import com.infra.adapter.CotacaoAdapter;
import com.mappers.AcaoMapper;
import com.repositories.AcaoRepository;
import com.repositories.CarteiraAcaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AcaoService {

    @Autowired
    private List<CotacaoAdapter> adapters;

    @Autowired
    private AcaoRepository acaoRepository;

    @Autowired
    private CarteiraAcaoRepository carteiraAcaoRepository;

    @Autowired
    private AcaoMapper acaoMapper;

    private CotacaoAdapter resolverAdapter(String mercado) {
        return adapters.stream()
                .filter(a -> a.suporta(mercado))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Mercado não suportado: " + mercado));
    }

    public AcaoResponseDTO cadastrar(AcaoRequestDTO request) {


        if (acaoRepository.existsByTicker(request.getTicker().toUpperCase())) {
            throw new RuntimeException("Ação já cadastrada com esse ticker");
        }


        AcaoResponseDTO cotacao = resolverAdapter(request.getMercado())
                .buscar(request.getTicker().toUpperCase());


        Acao acao = new Acao();
        acao.setTicker(cotacao.getTicker());
        acao.setNomeEmpresa(cotacao.getNomeEmpresa());
        acao.setMercado(request.getMercado().toUpperCase());
        acao.setMoeda(cotacao.getMoeda());
        acao.setCotacaoAtual(cotacao.getCotacaoAtual());
        acao.setDataHoraCotacao(cotacao.getDataHoraCotacao());

        Acao salva = acaoRepository.save(acao);

        return acaoMapper.toResponseDTO(salva);
    }

    public List<AcaoResponseDTO> listar() {
        return acaoRepository.findAll()
                .stream()
                .map(acaoMapper::toResponseDTO)
                .toList();
    }

    public AcaoResponseDTO buscarPorId(Long id) {
        Acao acao = acaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ação não encontrada"));
        return acaoMapper.toResponseDTO(acao);
    }

    public AcaoResponseDTO buscarPorTicker(String ticker) {
        Acao acao = acaoRepository.findByTicker(ticker.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Ação não encontrada"));
        return acaoMapper.toResponseDTO(acao);
    }

    public void excluir(Long id) {
        Acao acao = acaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ação não encontrada"));

        if (carteiraAcaoRepository.existsByAcaoId(id)) {
            throw new RuntimeException("Não é possível excluir uma ação que está em uso em carteiras");
        }

        acaoRepository.delete(acao);
    }

    public AcaoResponseDTO atualizarCotacao(Long id) {


        Acao acao = acaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ação não encontrada"));


        AcaoResponseDTO cotacaoAtualizada = resolverAdapter(acao.getMercado())
                .buscar(acao.getTicker());
        acao.setCotacaoAtual(cotacaoAtualizada.getCotacaoAtual());
        acao.setDataHoraCotacao(LocalDateTime.now());
        return acaoMapper.toResponseDTO(acaoRepository.save(acao));
    }
}
