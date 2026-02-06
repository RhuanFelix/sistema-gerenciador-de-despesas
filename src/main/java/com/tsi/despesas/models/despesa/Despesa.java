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
    private String dataVencimento;
    private FormaPagamento formaPagamento;
    private Recorrencia recorrencia;
    private String categoriaId;

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
