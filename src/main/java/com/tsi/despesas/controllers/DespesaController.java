package com.tsi.despesas.controllers;

import com.tsi.despesas.models.despesa.DespesaRequestDTO;
import com.tsi.despesas.models.despesa.DespesaResponseDTO;
import com.tsi.despesas.services.DespesaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/despesa")
@Tag(name = "Despesa")
public class DespesaController {
    private final DespesaService despesaService;

    @GetMapping("/categoria/{id}")
    @Operation(summary = "Listar usuários por categoria", description = "Retorna todos os usuários da categoria especificada")
    public ResponseEntity<List<DespesaResponseDTO>> consultarDespesasPorCategoria(@PathVariable("id") String categoriaId) {
        return ResponseEntity.ok(despesaService.consultarDespesasPorCategoria(categoriaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespesaResponseDTO> consultarDespesaPorId(@PathVariable String id) {
        return ResponseEntity.ok(despesaService.consultarDespesaPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<DespesaResponseDTO>> consultarTodasAsDespesas() {
        return ResponseEntity.ok(despesaService.consultarTodasAsDespesas());
    }

    @PostMapping
    public ResponseEntity<DespesaResponseDTO> criarDespesa(@RequestBody DespesaRequestDTO despesa) {
        return ResponseEntity.status(HttpStatus.CREATED).body(despesaService.criar(despesa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DespesaResponseDTO> atualizarDespesa(@PathVariable String id, @RequestBody DespesaRequestDTO despesa) {
        return ResponseEntity.ok(despesaService.atualizar(id, despesa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDespesa(@PathVariable String id) {
        despesaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
