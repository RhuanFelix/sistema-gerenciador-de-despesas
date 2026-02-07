package com.tsi.despesas.controllers;

import com.tsi.despesas.models.categoria.CategoriaRequestDTO;
import com.tsi.despesas.models.categoria.CategoriaResponseDTO;
import com.tsi.despesas.services.CategoriaService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/categoria")
@Tag(name = "Categoria")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> consultarTodasAsCategorias() {
        return ResponseEntity.ok(categoriaService.consultarTodasAsCategorias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> consultarCategoriaPorId(@PathVariable String id){
        return ResponseEntity.ok(categoriaService.consultarCategoriaPorId(id));
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> criarCategoria (@RequestBody CategoriaRequestDTO categoria){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.criar(categoria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizarCategoria (@PathVariable String id, @RequestBody CategoriaRequestDTO categoria){
        return ResponseEntity.ok(categoriaService.atualizar(id, categoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable String id){
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }


}