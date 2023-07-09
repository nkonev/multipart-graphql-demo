package com.example.graphql.demo;

import graphql.schema.GraphQLScalarType;
import name.nkonev.multipart.spring.graphql.coercing.webflux.UploadCoercing;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@SpringBootApplication
public class ClientApplication  {

	public static void main(String[] args) {
        new SpringApplicationBuilder(ClientApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

//    @Bean
//    public RuntimeWiringConfigurer runtimeWiringConfigurerUpload() {
//        GraphQLScalarType uploadScalar = GraphQLScalarType.newScalar()
//                .name("Upload")
//                .coercing(new UploadCoercing())
//                .build();
//
//        return wiringBuilder -> wiringBuilder.scalar(uploadScalar);
//    }

}
