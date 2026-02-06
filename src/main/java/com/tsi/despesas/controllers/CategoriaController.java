package com.tsi.despesas.controllers;

import com.tsi.despesas.models.categoria.Categoria;
import com.tsi.despesas.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public List<Categoria> consultarTodasAsCategorias() {
        return categoriaService.consultarTodasAsCategorias();
    }

    @GetMapping("/{id}")
    public Categoria consultarCategoriaPorId(@PathVariable String id){
        return categoriaService.consultarCategoriaPorId(id);
    }

    @PostMapping
    public Categoria criarCategoria (@RequestBody Categoria categoria){
    return categoriaService.criar(categoria);
    }

    @PutMapping("/{id}")
    public Categoria atualizarCategoria (@PathVariable String id, @RequestBody Categoria categoria){
        return categoriaService.atualizar(id, categoria);
    }

    @DeleteMapping("/{id}")
    public void deletarCategoria(@PathVariable String id){
        categoriaService.deletar(id);
    }


}