package com.tsi.despesas.models.despesa;

import java.time.LocalDate;

public record DespesaRequestDTO(String nome, String descricao, double valor, LocalDate dataVencimento, String formaPagamento, String recorrencia, String categoriaId) {
}
