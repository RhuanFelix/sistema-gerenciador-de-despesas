package com.tsi.despesas.models.despesa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ektorp.support.CouchDbDocument;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Despesa extends CouchDbDocument {
    private String nome;
    private String descricao;
    private double valor;
    private LocalDate dataVencimento = LocalDate.now();
    private String formaPagamento;
    private String recorrencia;
    private String categoriaId;

    public static Despesa converterParaDespesa(DespesaRequestDTO dto) {
        return new Despesa(dto.nome(), dto.descricao(), dto.valor(), dto.dataVencimento(), dto.formaPagamento(), dto.recorrencia(), dto.categoriaId());
    }

    public static DespesaResponseDTO converterParaDespesaResponseDTO(Despesa despesa) {
        return new DespesaResponseDTO(despesa.getId(), despesa.getNome(), despesa.getDescricao(), despesa.getValor(), despesa.getDataVencimento(), despesa.getFormaPagamento(), despesa.getRecorrencia(), despesa.getCategoriaId());
    }

    @Override
    public String toString() {
        return "Despesa{" +
                "id='" + getId() + '\'' +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", valor=" + valor +
                ", dataVencimento='" + dataVencimento + '\'' +
                ", formaPagamento=" + formaPagamento +
                ", recorrencia=" + recorrencia +
                ", categoriaId='" + categoriaId + '\'' +
                '}';
    }
}
