package com.tsi.despesas.configs;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;

@Configuration
public class CouchDBConfig {
    @Bean
    public CouchDbConnector categoriaDB() throws MalformedURLException {
        HttpClient httpClient = new StdHttpClient.Builder()
                .url("http://localhost:5984")
                .username("rhuan")
                .password("couchdev2026!")
                .build();

        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
        CouchDbConnector db = new StdCouchDbConnector("db_categoria", dbInstance);

        db.createDatabaseIfNotExists();

        return db;
    }

    @Bean
    public CouchDbConnector despesaDB() throws MalformedURLException {
        HttpClient httpClient = new StdHttpClient.Builder()
                .url("http://localhost:5984")
                .username("rhuan")
                .password("couchdev2026!")
                .build();

        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
        CouchDbConnector db = new StdCouchDbConnector("db_despesa", dbInstance);

        db.createDatabaseIfNotExists();

        return db;
    }
}
