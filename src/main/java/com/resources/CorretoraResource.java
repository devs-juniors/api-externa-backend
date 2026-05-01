package com.resources;

import com.infra.client.cep.dto.CepResponseDTO;
import com.services.CorretoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/corretoras")
public class CorretoraResource {

    @Autowired
    private CorretoraService corretoraService;


    @GetMapping("/cep/{cep}")
    public ResponseEntity<CepResponseDTO> buscarEnderecoPorCep(
            @PathVariable String cep) {

        CepResponseDTO endereco = corretoraService.buscarEnderecoPorCep(cep);
        return ResponseEntity.ok(endereco);
    }
}
