package com.tsi.despesas.services;

import com.tsi.despesas.models.categoria.Categoria;
import com.tsi.despesas.models.categoria.CategoriaRequestDTO;
import com.tsi.despesas.models.categoria.CategoriaResponseDTO;
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

    public CategoriaResponseDTO criar(CategoriaRequestDTO dto) {
        Categoria categoria = Categoria.converterParaCategoria(dto);
        categoriaRepository.add(categoria);
        System.out.println("Categoria criada com sucesso!");
        return Categoria.converterParaCategoriaResponseDTO(categoria);
    }

    public List<CategoriaResponseDTO> consultarTodasAsCategorias() {
        return categoriaRepository.getAll().stream().map(Categoria::converterParaCategoriaResponseDTO).toList();
    }

    public CategoriaResponseDTO consultarCategoriaPorId(String id) {
        Categoria categoria = categoriaRepository.get(id);
        if (categoria == null) {
            throw new RuntimeException("Categoria não encontrada com o id: " + id);
        }
        return Categoria.converterParaCategoriaResponseDTO(categoria);
    }

    public Categoria consultarCategoriaEntityPorId(String id) {
        Categoria categoria = categoriaRepository.get(id);
        if (categoria == null) {
            throw new RuntimeException("Categoria não encontrada com o id: " + id);
        }
        return categoria;
    }

    public CategoriaResponseDTO atualizar(String id, CategoriaRequestDTO dto) {
        Categoria categoriaExistente = categoriaRepository.get(id);
        if (categoriaExistente == null) {
            throw new RuntimeException("Categoria não encontrada com o id: " + id);
        }
        categoriaExistente.setNome(dto.nome());
        categoriaExistente.setDescricao(dto.descricao());
        categoriaRepository.update(categoriaExistente);
        return new CategoriaResponseDTO(
                categoriaExistente.getId(),
                categoriaExistente.getNome(),
                categoriaExistente.getDescricao()
        );
    }

    public void deletar(String id) {
        Categoria categoriaExistente = categoriaRepository.get(id);
        if (categoriaExistente == null) {
            throw new RuntimeException("Categoria não encontrada com o id: " + id);
        }

        List<Despesa> despesas = despesaRepository.findByCategoriaId(id);
        for (Despesa despesa : despesas) {
            despesaRepository.remove(despesa);
        }

        categoriaRepository.remove(categoriaExistente);
    }
}
