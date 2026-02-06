package com.tsi.despesas.services;

import com.tsi.despesas.models.categoria.Categoria;
import com.tsi.despesas.models.despesa.Despesa;
import com.tsi.despesas.repositories.DespesaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DespesaService {
    private final DespesaRepository despesaRepository;
    private final CategoriaService categoriaService;

    public Despesa criar(Despesa despesa) {
        Categoria categoriaExistente = categoriaService.consultarCategoriaPorId(despesa.getCategoriaId());
        if (categoriaExistente == null) {
            throw new RuntimeException("Categoria não encontrada com o id: " + despesa.getCategoriaId());
        }
        despesaRepository.add(despesa);
        System.out.println("A despesa foi criada com sucesso!");
        return despesa;
    }

    public List<Despesa> consultarDespesasPorCategoria(String categoriaId) {
        Categoria categoriaExistente = categoriaService.consultarCategoriaPorId(categoriaId);
        if (categoriaExistente == null) {
            throw new RuntimeException("Não existe categoria com o id: " + categoriaId);
        }
        return despesaRepository.findByCategoriaId(categoriaId);
    }

    public List<Despesa> consultarTodasAsDespesas() {
        return despesaRepository.getAll();
    }

    public Despesa consultarDespesaPorId(String id) {
        return despesaRepository.get(id);
    }

    public Despesa atualizar(String id, Despesa despesa) {
        Despesa despesaExistente = despesaRepository.get(id);
        if (despesaExistente == null) {
            throw new RuntimeException("Não existe categoria com o id: " + id);
        }
        if (!despesaExistente.getCategoriaId().equals(despesa.getCategoriaId())) {
            Categoria categoria = categoriaService.consultarCategoriaPorId(despesa.getCategoriaId());
            if (categoria == null) {
                throw new RuntimeException("Categoria não encontrada com ID: " + despesa.getCategoriaId());
            }
            despesaExistente.setNome(despesa.getNome());
            despesaExistente.setDescricao(despesa.getDescricao());
            despesaExistente.setValor(despesa.getValor());
            despesaExistente.setDataVencimento(despesa.getDataVencimento());
            despesaExistente.setRecorrencia(despesa.getRecorrencia());
            despesaExistente.setCategoriaId(despesa.getCategoriaId());
            despesaRepository.update(despesaExistente);
            return despesaExistente;
        }
        return null;
    }

    public void deletar(String id) {
        Despesa despesaExistente = despesaRepository.get(id);
        if (despesaExistente == null) {
            throw new RuntimeException("Não existe categoria com o id: " + id);
        } else {
            despesaRepository.remove(despesaExistente);
        }
    }
}
