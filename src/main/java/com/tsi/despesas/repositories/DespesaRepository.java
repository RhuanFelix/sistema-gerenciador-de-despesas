package com.tsi.despesas.repositories;

import com.tsi.despesas.models.despesa.Despesa;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.GenerateView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DespesaRepository extends CouchDbRepositorySupport<Despesa> {
    public DespesaRepository(@Qualifier("despesaDB") CouchDbConnector db) {
        super(Despesa.class, db);
        initStandardDesignDocument();
    }

    @GenerateView
    public List<Despesa> findByCategoriaId(String categoriaId) {
        return queryView("by_categoriaId", categoriaId);
    }
}
