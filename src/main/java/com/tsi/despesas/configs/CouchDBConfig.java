package com.tsi.despesas.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.ObjectMapperFactory;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;

@Configuration
public class CouchDBConfig {
    @Value("${couchdb.url}")
    private String url;
    @Value("${couchdb.username}")
    private String nomeUsuario;
    @Value("${couchdb.password}")
    private String senha;

    private CouchDbConnector createConnector(String dbName) throws MalformedURLException {
        HttpClient httpClient = new StdHttpClient.Builder()
                .url(url)
                .username(nomeUsuario)
                .password(senha)
                .build();

        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);

        return getStdCouchDbConnector(dbName, dbInstance);
    }

    private static @NonNull StdCouchDbConnector getStdCouchDbConnector(String dbName, CouchDbInstance dbInstance) {
        ObjectMapperFactory omFactory = new ObjectMapperFactory() {

            private ObjectMapper createMapper() {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                return mapper;
            }

            @Override
            public ObjectMapper createObjectMapper() {
                return createMapper();
            }

            @Override
            public ObjectMapper createObjectMapper(CouchDbConnector couchDbConnector) {
                return createMapper();
            }
        };


        StdCouchDbConnector connector = new StdCouchDbConnector(dbName, dbInstance, omFactory);

        connector.createDatabaseIfNotExists();
        return connector;
    }

    @Bean
    public CouchDbConnector categoriaDB() throws MalformedURLException {
        return createConnector("db_categoria");
    }

    @Bean
    public CouchDbConnector despesaDB() throws MalformedURLException {
        return createConnector("db_despesa");
    }
}
