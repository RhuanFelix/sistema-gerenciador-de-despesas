package com.tsi.despesas.services;

import com.tsi.despesas.models.categoria.Categoria;
import com.tsi.despesas.models.despesa.Despesa;
import com.tsi.despesas.repositories.CategoriaRepository;
import com.tsi.despesas.repositories.DespesaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final DespesaRepository despesaRepository;

    public Categoria criar(Categoria categoria) {
        categoriaRepository.add(categoria);
        System.out.println("Categoria criada com sucesso!");
        return categoria;
    }

    public List<Categoria> consultarTodasAsCategorias() {
        return categoriaRepository.getAll();
    }

    public Categoria consultarCategoriaPorId(String id) {
        return categoriaRepository.get(id);
    }

    public Categoria atualizar(String id, Categoria categoria) {
        Categoria categoriaExistente = categoriaRepository.get(id);
        if (categoriaExistente == null) {
            throw new RuntimeException("Não existe categoria com o id: " + id);
        } else {
            categoriaExistente.setNome(categoria.getNome());
            categoriaExistente.setDescricao(categoria.getDescricao());
            categoriaRepository.update(categoriaExistente);
            return categoriaExistente;
        }
    }

    public void deletar(String id) {
        Categoria categoriaExistente = categoriaRepository.get(id);
        if (categoriaExistente == null) {
            System.out.printf("Não existe categoria com o id: %s\n", id);
        } else {
            List<Despesa> despesas = despesaRepository.findByCategoriaId(id);
            for (Despesa despesa : despesas) {
                despesaRepository.remove(despesa);
            }
            categoriaRepository.remove(categoriaExistente);
        }
    }
}
