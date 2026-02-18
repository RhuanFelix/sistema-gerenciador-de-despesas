package com.tsi.despesas.services;

import com.tsi.despesas.models.categoria.Categoria;
import com.tsi.despesas.models.despesa.Despesa;
import com.tsi.despesas.models.despesa.DespesaRequestDTO;
import com.tsi.despesas.models.despesa.DespesaResponseDTO;
import com.tsi.despesas.repositories.DespesaRepository;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class DespesaService {
    private final DespesaRepository despesaRepository;
    private final CategoriaService categoriaService;
    private final RateLimiter despesaRateLimiter;

    private <T> T executarRateLimiterComRetorno(Supplier<T> supplier) {
        try {
            return despesaRateLimiter.executeSupplier(supplier);
        } catch (RequestNotPermitted e) {
            throw new ResponseStatusException(
                    HttpStatus.TOO_MANY_REQUESTS,
                    "Muitas requisições para o serviço de despesas. Aguarde um momento."
            );
        }
    }

    private void executarRateLimiterSemRetorno(Runnable runnable) {
        try {
            despesaRateLimiter.executeRunnable(runnable);
        } catch (RequestNotPermitted e) {
            throw new ResponseStatusException(
                    HttpStatus.TOO_MANY_REQUESTS,
                    "Muitas requisições para o serviço de despesas. Aguarde um momento."
            );
        }
    }

    public DespesaResponseDTO criar(DespesaRequestDTO dto) {
        return executarRateLimiterComRetorno(() -> {
            Despesa despesa = Despesa.converterParaDespesa(dto);
            Categoria categoriaExistente = categoriaService.consultarCategoriaEntityPorId(despesa.getCategoriaId());
            if (categoriaExistente == null) {
                throw new RuntimeException("Categoria não encontrada com o id: " + despesa.getCategoriaId());
            }
            despesaRepository.add(despesa);
            System.out.println("A despesa foi criada com sucesso!");
            return Despesa.converterParaDespesaResponseDTO(despesa);
        });
    }

    public List<DespesaResponseDTO> consultarDespesasPorCategoria(String categoriaId) {
        return executarRateLimiterComRetorno(() -> {
            Categoria categoriaExistente = categoriaService.consultarCategoriaEntityPorId(categoriaId);
            if (categoriaExistente == null) {
                throw new RuntimeException("Categoria não encontrada com o id: " + categoriaId);
            }
            List<Despesa> despesas = despesaRepository.findByCategoriaId(categoriaId);
            return despesas.stream().map(Despesa::converterParaDespesaResponseDTO).toList();
        });
    }

    public List<DespesaResponseDTO> consultarTodasAsDespesas() {
        return executarRateLimiterComRetorno(() ->
                despesaRepository.getAll().stream()
                        .map(Despesa::converterParaDespesaResponseDTO)
                        .toList()
        );
    }

    public DespesaResponseDTO consultarDespesaPorId(String id) {
        return executarRateLimiterComRetorno(() -> {
            Despesa despesa = despesaRepository.get(id);
            if (despesa == null) {
                throw new RuntimeException("Despesa não encontrada com o id: " + id);
            }
            return Despesa.converterParaDespesaResponseDTO(despesa);
        });
    }

    public DespesaResponseDTO atualizar(String id, DespesaRequestDTO dto) {
        return executarRateLimiterComRetorno(() -> {
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
        });
    }

    public void deletar(String id) {
        executarRateLimiterSemRetorno(() -> {
            Despesa despesaExistente = despesaRepository.get(id);
            if (despesaExistente == null) {
                throw new RuntimeException("Despesa não encontrada com o id: " + id);
            } else {
                despesaRepository.remove(despesaExistente);
            }
        });
    }
}