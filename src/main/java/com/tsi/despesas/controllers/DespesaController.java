package com.tsi.despesas.controllers;

import com.tsi.despesas.models.despesa.Despesa;
import com.tsi.despesas.services.DespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/despesa")

public class DespesaController {
    @Autowired
    private DespesaService despesaService;

    @GetMapping("/{id}")
    public List<Despesa> consultarDespesasPorCategoria(@PathVariable String categoriaId) {
        return despesaService.consultarDespesasPorCategoria(categoriaId);
    }

    @GetMapping("/{id}")
    public Despesa consultarDespesaPorId(@PathVariable String id) {
        return despesaService.consultarDespesaPorId(id);
    }

    @GetMapping
    public List<Despesa> consultarTodasAsDespesas() {
        return despesaService.consultarTodasAsDespesas();
    }

    @PostMapping
    public Despesa criarDespesa(@RequestBody Despesa despesa) {
        return despesaService.criar(despesa);
    }

    @PutMapping("/{id}")
    public Despesa atualizarDespesa(@PathVariable String id, @RequestBody Despesa despesa) {
        return despesaService.atualizar(id, despesa);
    }

    @DeleteMapping("/{id}")
    public void deletarDespesa(@PathVariable String id) {
        despesaService.deletar(id);
    }

}
