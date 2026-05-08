package com.resources;

import com.domains.dtos.CarteiraRequestDTO;
import com.domains.dtos.CarteiraResponseDTO;
import com.domains.dtos.OperacaoRequestDTO;
import com.domains.dtos.OperacaoResponseDTO;
import com.services.CarteiraService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carteiras")
public class CarteiraResource {

    @Autowired
    private CarteiraService carteiraService;


    @PostMapping
    public ResponseEntity<CarteiraResponseDTO> cadastrar(
            @RequestBody @Valid CarteiraRequestDTO request) {

        CarteiraResponseDTO response = carteiraService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CarteiraResponseDTO>> listar() {
        List<CarteiraResponseDTO> carteiras = carteiraService.listar();
        return ResponseEntity.ok(carteiras);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarteiraResponseDTO> buscarPorId(
            @PathVariable Long id) {

        CarteiraResponseDTO carteira = carteiraService.buscarPorId(id);
        return ResponseEntity.ok(carteira);
    }

    @PostMapping("/{id}/comprar")
    public ResponseEntity<OperacaoResponseDTO> comprar(
            @PathVariable Long id,
            @RequestBody @Valid OperacaoRequestDTO request) {

        OperacaoResponseDTO operacao = carteiraService.comprar(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(operacao);
    }

    @PostMapping("/{id}/vender")
    public ResponseEntity<OperacaoResponseDTO> vender(
            @PathVariable Long id,
            @RequestBody @Valid OperacaoRequestDTO request) {

        OperacaoResponseDTO operacao = carteiraService.vender(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(operacao);
    }

    @GetMapping("/{id}/operacoes")
    public ResponseEntity<List<OperacaoResponseDTO>> listarOperacoes(
            @PathVariable Long id) {

        List<OperacaoResponseDTO> operacoes = carteiraService.listarOperacoes(id);
        return ResponseEntity.ok(operacoes);
    }
}
