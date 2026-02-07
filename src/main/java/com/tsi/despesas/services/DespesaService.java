package com.tsi.despesas.services;

import com.tsi.despesas.models.categoria.Categoria;
import com.tsi.despesas.models.despesa.Despesa;
import com.tsi.despesas.models.despesa.DespesaRequestDTO;
import com.tsi.despesas.models.despesa.DespesaResponseDTO;
import com.tsi.despesas.repositories.DespesaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class DespesaService {
    private final DespesaRepository despesaRepository;
    private final CategoriaService categoriaService;

    public DespesaResponseDTO criar(DespesaRequestDTO dto) {
        Despesa despesa = Despesa.converterParaDespesa(dto);
        Categoria categoriaExistente = categoriaService.consultarCategoriaEntityPorId(despesa.getCategoriaId());
        if (categoriaExistente == null) {
            throw new RuntimeException("Categoria não encontrada com o id: " + despesa.getCategoriaId());
        }
        despesaRepository.add(despesa);
        System.out.println("A despesa foi criada com sucesso!");
        return Despesa.converterParaDespesaResponseDTO(despesa);
    }

    public List<DespesaResponseDTO> consultarDespesasPorCategoria(String categoriaId) {
        Categoria categoriaExistente = categoriaService.consultarCategoriaEntityPorId(categoriaId);
        if (categoriaExistente == null) {
            throw new RuntimeException("Categoria não encontrada com o id: " + categoriaId);
        }
        List<Despesa> despesas = despesaRepository.findByCategoriaId(categoriaId);
        return despesas.stream().map(Despesa::converterParaDespesaResponseDTO).toList();
    }

    public List<DespesaResponseDTO> consultarTodasAsDespesas() {
        return despesaRepository.getAll().stream().map(Despesa::converterParaDespesaResponseDTO).toList();
    }

    public DespesaResponseDTO consultarDespesaPorId(String id) {
        Despesa despesa = despesaRepository.get(id);
        if (despesa == null) {
            throw new RuntimeException("Despesa não encontrada com o id: " + id);
        }
        return Despesa.converterParaDespesaResponseDTO(despesa);
    }

    public DespesaResponseDTO atualizar(String id, DespesaRequestDTO dto) {
        Despesa despesaExistente = despesaRepository.get(id);
        if (despesaExistente == null) {
            throw new RuntimeException("Despesa não encontrada com o id: " + id);
        }

        if (!despesaExistente.getCategoriaId().equals(dto.categoriaId())) {
            Categoria categoria = categoriaService.consultarCategoriaEntityPorId(dto.categoriaId());
            if (categoria == null) {
                throw new RuntimeException("Categoria não encontrada com ID: " + dto.categoriaId());
            }
        }
        despesaExistente.setNome(dto.nome());
        despesaExistente.setDescricao(dto.descricao());
        despesaExistente.setValor(dto.valor());
        despesaExistente.setDataVencimento(LocalDate.now());
        despesaExistente.setRecorrencia(dto.recorrencia());
        despesaExistente.setCategoriaId(dto.categoriaId());
        despesaRepository.update(despesaExistente);
        return Despesa.converterParaDespesaResponseDTO(despesaExistente);
    }

    public void deletar(String id) {
        Despesa despesaExistente = despesaRepository.get(id);
        if (despesaExistente == null) {
            throw new RuntimeException("Despesa não encontrada com o id: " + id);
        } else {
            despesaRepository.remove(despesaExistente);
        }
    }
}
