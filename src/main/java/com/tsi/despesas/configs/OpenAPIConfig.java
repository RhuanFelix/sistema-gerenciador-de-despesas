package com.tsi.despesas.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI personalizarOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gerenciador de despesas pessoais")
                        .description("""
                                Este projeto faz parte de um seminário da disciplina de Banco de Dados II. Possui o objetivo de criar uma API Rest com Spring Boot utilizando o banco de dados não relacional: Apache CouchDB.
                                
                                O sistema possui duas entidades: categoria e despesa.
                                
                                ## Funcionalidades
                                
                                O gerenciador de despesas pessoais possui como funcionalidade:
                                
                                - Criar, atualizar, consultar (listando todas ou por id) e deletar categorias;
                                - Criar, atualizar, consultar (listando todas, ou por id ou listando de uma categoria) e deletar despesas.
                                
                                ## Tecnologias utilizadas
                                
                                - Spring Boot
                                - Spring Web
                                - Spring Doc OpenAPI
                                - Ektorp (CouchDB)
                                - HTTP Client
                                - Lombok
                                - Jackson Datatype: JSR310
                                """));
    }
}
