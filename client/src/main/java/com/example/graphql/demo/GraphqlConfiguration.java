package com.example.graphql.demo;

import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.coercing.webflux.UploadCoercing;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphqlConfiguration {

    // TODO add to Spring Boot - seems it's possible not to declare scalar in schema but declare here - make it disableable
    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurerUpload() {

        GraphQLScalarType uploadScalar = GraphQLScalarType.newScalar()
                .name("Upload")
                .coercing(new UploadCoercing())
                .build();

        return wiringBuilder -> wiringBuilder.scalar(uploadScalar);
    }

}
