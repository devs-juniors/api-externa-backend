package com.services;

import com.domains.Corretora;
import com.domains.dtos.CorretoraRequestDTO;
import com.domains.dtos.CorretoraResponseDTO;
import com.infra.client.cep.dto.CepResponseDTO;
import com.infra.client.cnpj.dto.CnpjResponseDTO;
import com.infra.facade.CepFacade;
import com.infra.facade.CnpjFacade;
import com.mappers.CorretoraMapper;
import com.repositories.CarteiraRepository;
import com.repositories.CorretoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorretoraService {

    @Autowired
    private CnpjFacade cnpjFacade;

    @Autowired
    private CepFacade cepFacade;

    @Autowired
    private CorretoraRepository corretoraRepository;

    @Autowired
    private CarteiraRepository carteiraRepository;

    @Autowired
    private CorretoraMapper corretoraMapper;

    public CorretoraResponseDTO cadastrar(CorretoraRequestDTO request) {

  
        String cnpjLimpo = request.getCnpj().replaceAll("[.\\-/]", "");


        if (corretoraRepository.existsByCnpj(cnpjLimpo)) {
            throw new RuntimeException("Corretora já cadastrada com esse CNPJ");
        }


        CnpjResponseDTO dadosCnpj = cnpjFacade.buscar(cnpjLimpo);

        // Regra simplificada de elegibilidade CVM: situação ATIVA + natureza jurídica do tipo sociedade
        boolean ativa = "ATIVA".equalsIgnoreCase(dadosCnpj.getSituacaoCadastral());
        boolean sociedade = dadosCnpj.getNaturezaJuridica() != null
                && dadosCnpj.getNaturezaJuridica().toUpperCase().contains("SOCIEDADE");

        if (!ativa || !sociedade) {
            throw new RuntimeException("Corretora não é validada na CVM e não pode ser cadastrada");
        }

        CepResponseDTO dadosCep = cepFacade.buscar(dadosCnpj.getCep());


        Corretora corretora = new Corretora();
        corretora.setCnpj(cnpjLimpo);
        corretora.setRazaoSocial(dadosCnpj.getRazaoSocial());
        corretora.setNomeFantasia(dadosCnpj.getNomeFantasia());
        corretora.setEmail(dadosCnpj.getEmail());
        corretora.setTelefone(dadosCnpj.getTelefone());
        corretora.setSituacaoCadastral(dadosCnpj.getSituacaoCadastral());
        corretora.setCep(dadosCep.getCep());
        corretora.setLogradouro(dadosCep.getLogradouro());
        corretora.setNumero(dadosCnpj.getNumero());
        corretora.setComplemento(dadosCep.getComplemento());
        corretora.setBairro(dadosCep.getBairro());
        corretora.setCidade(dadosCep.getCidade());
        corretora.setUf(dadosCep.getUf());
        corretora.setValidadaNaCvm(true);


        Corretora salva = corretoraRepository.save(corretora);

        return corretoraMapper.toResponseDTO(salva);
    }

    public List<CorretoraResponseDTO> listar() {
        return corretoraRepository.findAll()
                .stream()
                .map(corretoraMapper::toResponseDTO)
                .toList();
    }

    public CorretoraResponseDTO buscarPorId(Long id) {
        Corretora corretora = corretoraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Corretora não encontrada"));
        return corretoraMapper.toResponseDTO(corretora);
    }

    public CorretoraResponseDTO buscarPorCnpj(String cnpj) {
        String cnpjLimpo = cnpj.replaceAll("[.\\-/]", "");
        Corretora corretora = corretoraRepository.findByCnpj(cnpjLimpo)
                .orElseThrow(() -> new RuntimeException("Corretora não encontrada"));
        return corretoraMapper.toResponseDTO(corretora);
    }

    public void excluir(Long id) {
        Corretora corretora = corretoraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Corretora não encontrada"));

        if (carteiraRepository.existsByCorretoraId(id)) {
            throw new RuntimeException("Não é possível excluir uma corretora que possui carteiras vinculadas");
        }

        corretoraRepository.delete(corretora);
    }

    public CepResponseDTO buscarEnderecoPorCep(String cep) {
        return cepFacade.buscar(cep);
    }
}