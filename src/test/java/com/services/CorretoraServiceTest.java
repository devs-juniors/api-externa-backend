package com.services;

import com.domains.Corretora;
import com.domains.dtos.CorretoraRequestDTO;
import com.domains.dtos.CorretoraResponseDTO;
import com.infra.client.cep.dto.CepResponseDTO;
import com.infra.client.cnpj.dto.CnpjResponseDTO;
import com.infra.facade.CepFacade;
import com.infra.facade.CnpjFacade;
import com.infra.facade.CvmFacade;
import com.mappers.CorretoraMapper;
import com.repositories.CorretoraRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CorretoraServiceTest {

    @Mock
    private CnpjFacade cnpjFacade;

    @Mock
    private CepFacade cepFacade;

    @Mock
    private CvmFacade cvmFacade;

    @Mock
    private CorretoraRepository corretoraRepository;

    @Mock
    private CorretoraMapper corretoraMapper;

    @InjectMocks
    private CorretoraService corretoraService;

    @Test
    void deveSalvarCorretoraComValidadaNaCvmTrueQuandoElegivelNaCvm() {
        CorretoraRequestDTO request = new CorretoraRequestDTO("12345678000195");

        when(corretoraRepository.existsByCnpj("12345678000195")).thenReturn(false);
        when(cnpjFacade.buscar("12345678000195")).thenReturn(cnpjResponseMock());
        when(cepFacade.buscar("01310100")).thenReturn(cepResponseMock());

        ArgumentCaptor<Corretora> corretoraCaptor = ArgumentCaptor.forClass(Corretora.class);
        when(corretoraRepository.save(corretoraCaptor.capture())).thenAnswer(i -> i.getArgument(0));
        when(corretoraMapper.toResponseDTO(any())).thenReturn(new CorretoraResponseDTO());

        corretoraService.cadastrar(request);

        assertThat(corretoraCaptor.getValue().getValidadaNaCvm()).isTrue();
    }

    @Test
    void deveLancarExcecaoQuandoCorretoraInativaOuNaoESociedade() {
        CorretoraRequestDTO request = new CorretoraRequestDTO("12345678000195");

        CnpjResponseDTO cnpjInvalido = cnpjResponseMock();
        cnpjInvalido.setSituacaoCadastral("BAIXADA");

        when(corretoraRepository.existsByCnpj("12345678000195")).thenReturn(false);
        when(cnpjFacade.buscar("12345678000195")).thenReturn(cnpjInvalido);

        assertThatThrownBy(() -> corretoraService.cadastrar(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Corretora não é validada na CVM e não pode ser cadastrada");
    }

    private CnpjResponseDTO cnpjResponseMock() {
        CnpjResponseDTO dto = new CnpjResponseDTO();
        dto.setRazaoSocial("XP INVESTIMENTOS S.A.");
        dto.setNomeFantasia("XP");
        dto.setEmail("contato@xp.com.br");
        dto.setTelefone("1140044242");
        dto.setCep("01310100");
        dto.setNumero("1000");
        dto.setSituacaoCadastral("ATIVA");
        dto.setNaturezaJuridica("SOCIEDADE ANÔNIMA");
        return dto;
    }

    private CepResponseDTO cepResponseMock() {
        CepResponseDTO dto = new CepResponseDTO();
        dto.setCep("01310100");
        dto.setLogradouro("Av. Paulista");
        dto.setComplemento("");
        dto.setBairro("Bela Vista");
        dto.setCidade("São Paulo");
        dto.setUf("SP");
        return dto;
    }
}
