package com.services;

import com.infra.client.cep.dto.CepResponseDTO;
import com.infra.facade.CepFacade;
import com.mappers.CorretoraMapper;
import com.repositories.CorretoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CorretoraService {

    @Autowired
    private CepFacade cepFacade;

    @Autowired
    private CorretoraRepository corretoraRepository;

    @Autowired
    private CorretoraMapper corretoraMapper;

    public CepResponseDTO buscarEnderecoPorCep(String cep) {
        return cepFacade.buscar(cep);
    }
}