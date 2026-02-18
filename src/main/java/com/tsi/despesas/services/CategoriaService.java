package com.tsi.despesas.services;

import com.tsi.despesas.models.categoria.Categoria;
import com.tsi.despesas.models.categoria.CategoriaRequestDTO;
import com.tsi.despesas.models.categoria.CategoriaResponseDTO;
import com.tsi.despesas.models.despesa.Despesa;
import com.tsi.despesas.repositories.CategoriaRepository;
import com.tsi.despesas.repositories.DespesaRepository;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final DespesaRepository despesaRepository;
    private final RateLimiter categoriaRateLimiter;

    private <T> T executarRateLimiterComRetorno(Supplier<T> supplier) {
        try {
            return categoriaRateLimiter.executeSupplier(supplier);
        } catch (RequestNotPermitted e) {
            throw new ResponseStatusException(
                    HttpStatus.TOO_MANY_REQUESTS,
                    "Muitas requisições para o serviço de categorias. Aguarde um momento."
            );
        }
    }

    private void executarRateLimiterSemRetorno(Runnable runnable) {
        try {
            categoriaRateLimiter.executeRunnable(runnable);
        } catch (RequestNotPermitted e) {
            throw new ResponseStatusException(
                    HttpStatus.TOO_MANY_REQUESTS,
                    "Muitas requisições para o serviço de categorias. Aguarde um momento."
            );
        }
    }

    public CategoriaResponseDTO criar(CategoriaRequestDTO dto) {
        return executarRateLimiterComRetorno(() -> {
            Categoria categoria = Categoria.converterParaCategoria(dto);
            categoriaRepository.add(categoria);
            System.out.println("Categoria criada com sucesso!");
            return Categoria.converterParaCategoriaResponseDTO(categoria);
        });
    }

    public List<CategoriaResponseDTO> consultarTodasAsCategorias() {
        return executarRateLimiterComRetorno(() ->
                categoriaRepository.getAll().stream()
                        .map(Categoria::converterParaCategoriaResponseDTO)
                        .toList()
        );
    }

    public CategoriaResponseDTO consultarCategoriaPorId(String id) {
        return executarRateLimiterComRetorno(() -> {
            Categoria categoria = categoriaRepository.get(id);
            if (categoria == null) {
                throw new RuntimeException("Categoria não encontrada com o id: " + id);
            }
            return Categoria.converterParaCategoriaResponseDTO(categoria);
        });
    }

    public Categoria consultarCategoriaEntityPorId(String id) {
        return executarRateLimiterComRetorno(() -> {
            Categoria categoria = categoriaRepository.get(id);
            if (categoria == null) {
                throw new RuntimeException("Categoria não encontrada com o id: " + id);
            }
            return categoria;
        });
    }

    public CategoriaResponseDTO atualizar(String id, CategoriaRequestDTO dto) {
        return executarRateLimiterComRetorno(() -> {
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
        });
    }

    public void deletar(String id) {
        executarRateLimiterSemRetorno(() -> {
            Categoria categoriaExistente = categoriaRepository.get(id);
            if (categoriaExistente == null) {
                throw new RuntimeException("Categoria não encontrada com o id: " + id);
            }

            List<Despesa> despesas = despesaRepository.findByCategoriaId(id);
            for (Despesa despesa : despesas) {
                despesaRepository.remove(despesa);
            }

            categoriaRepository.remove(categoriaExistente);
        });
    }
}