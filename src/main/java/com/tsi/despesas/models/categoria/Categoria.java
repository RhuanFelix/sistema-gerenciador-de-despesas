package com.tsi.despesas.models.categoria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ektorp.support.CouchDbDocument;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Categoria extends CouchDbDocument {
    private String nome;
    private String descricao;

    @Override
    public String toString() {
        return "Categoria{" +
                "id='" + getId() + '\'' +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
