package com.resources;

import com.domains.dtos.CorretoraRequestDTO;
import com.domains.dtos.CorretoraResponseDTO;
import com.infra.client.cep.dto.CepResponseDTO;
import com.services.CorretoraService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/corretoras")
public class CorretoraResource {

        @Autowired
        private CorretoraService corretoraService;

        @PostMapping
        public ResponseEntity<CorretoraResponseDTO> cadastrar(
                @RequestBody @Valid CorretoraRequestDTO request) {

            CorretoraResponseDTO response = corretoraService.cadastrar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }


        @GetMapping
        public ResponseEntity<List<CorretoraResponseDTO>> listar() {
            List<CorretoraResponseDTO> corretoras = corretoraService.listar();
            return ResponseEntity.ok(corretoras);
        }


        @GetMapping("/{id}")
        public ResponseEntity<CorretoraResponseDTO> buscarPorId(
                @PathVariable Long id) {

            CorretoraResponseDTO corretora = corretoraService.buscarPorId(id);
            return ResponseEntity.ok(corretora);
        }


        @GetMapping("/cnpj/{cnpj}")
        public ResponseEntity<CorretoraResponseDTO> buscarPorCnpj(
                @PathVariable String cnpj) {

            CorretoraResponseDTO corretora = corretoraService.buscarPorCnpj(cnpj);
            return ResponseEntity.ok(corretora);
        }


        @GetMapping("/cep/{cep}")
        public ResponseEntity<CepResponseDTO> buscarEnderecoPorCep(
                @PathVariable String cep) {

            CepResponseDTO endereco = corretoraService.buscarEnderecoPorCep(cep);
            return ResponseEntity.ok(endereco);
        }


        @DeleteMapping("/{id}")
        public ResponseEntity<Void> excluir(@PathVariable Long id) {
            corretoraService.excluir(id);
            return ResponseEntity.noContent().build();
        }

}
