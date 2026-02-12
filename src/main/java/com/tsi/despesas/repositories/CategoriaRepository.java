package com.tsi.despesas.repositories;

import com.tsi.despesas.models.categoria.Categoria;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CategoriaRepository extends CouchDbRepositorySupport<Categoria> {
    public CategoriaRepository(@Qualifier("categoriaDB") CouchDbConnector db) {
        super(Categoria.class, db);
        initStandardDesignDocument();
    }
}
