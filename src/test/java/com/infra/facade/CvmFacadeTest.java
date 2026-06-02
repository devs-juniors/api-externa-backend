package com.infra.facade;

import com.infra.client.cvm.CvmCorretoraClient;
import com.infra.client.cvm.dto.CvmCorretoraResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CvmFacadeTest {

    @Mock
    private CvmCorretoraClient cvmCorretoraClient;

    @InjectMocks
    private CvmFacade cvmFacade;

    @Test
    void deveRetornarTrueQuandoCorretoraEmFuncionamentoNormalNaCvm() {
        CvmCorretoraResponseDTO response = new CvmCorretoraResponseDTO();
        response.setStatus("EM FUNCIONAMENTO NORMAL");
        when(cvmCorretoraClient.buscarCorretora("12345678000195")).thenReturn(response);

        boolean resultado = cvmFacade.isValidadaNaCvm("12345678000195");

        assertThat(resultado).isTrue();
    }

    @Test
    void deveRetornarFalseQuandoCorretoraForCancelada() {
        CvmCorretoraResponseDTO response = new CvmCorretoraResponseDTO();
        response.setStatus("CANCELADA");
        when(cvmCorretoraClient.buscarCorretora("12345678000195")).thenReturn(response);

        boolean resultado = cvmFacade.isValidadaNaCvm("12345678000195");

        assertThat(resultado).isFalse();
    }

    @Test
    void deveRetornarFalseQuandoCnpjNaoEncontradoNaCvm() {
        when(cvmCorretoraClient.buscarCorretora("12345678000195"))
                .thenThrow(new RuntimeException("Recurso não encontrado na API externa"));

        boolean resultado = cvmFacade.isValidadaNaCvm("12345678000195");

        assertThat(resultado).isFalse();
    }
}
