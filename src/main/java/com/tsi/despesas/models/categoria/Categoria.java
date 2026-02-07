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

    public static Categoria converterParaCategoria(CategoriaRequestDTO dto) {
        return new Categoria(dto.nome(), dto.descricao());
    }

    public static CategoriaResponseDTO converterParaCategoriaResponseDTO(Categoria categoria) {
        return new CategoriaResponseDTO(categoria.getId(), categoria.getNome(), categoria.getDescricao());
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id='" + getId() + '\'' +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
