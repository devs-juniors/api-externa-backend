package com.resources;

import com.domains.dtos.AcaoRequestDTO;
import com.domains.dtos.AcaoResponseDTO;
import com.services.AcaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acoes")
public class AcaoResource {

    @Autowired
    private AcaoService acaoService;


    @PostMapping
    public ResponseEntity<AcaoResponseDTO> cadastrar(
            @RequestBody @Valid AcaoRequestDTO request) {

        AcaoResponseDTO response = acaoService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping
    public ResponseEntity<List<AcaoResponseDTO>> listar() {
        List<AcaoResponseDTO> acoes = acaoService.listar();
        return ResponseEntity.ok(acoes);
    }


    @GetMapping("/{id}")
    public ResponseEntity<AcaoResponseDTO> buscarPorId(
            @PathVariable Long id) {

        AcaoResponseDTO acao = acaoService.buscarPorId(id);
        return ResponseEntity.ok(acao);
    }


    @GetMapping("/ticker/{ticker}")
    public ResponseEntity<AcaoResponseDTO> buscarPorTicker(
            @PathVariable String ticker) {

        AcaoResponseDTO acao = acaoService.buscarPorTicker(ticker);
        return ResponseEntity.ok(acao);
    }


    @PutMapping("/{id}/atualizar-cotacao")
    public ResponseEntity<AcaoResponseDTO> atualizarCotacao(
            @PathVariable Long id) {

        AcaoResponseDTO acao = acaoService.atualizarCotacao(id);
        return ResponseEntity.ok(acao);
    }
}
