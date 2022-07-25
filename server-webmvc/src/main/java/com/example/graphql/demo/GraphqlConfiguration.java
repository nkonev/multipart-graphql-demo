package com.example.graphql.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.schema.GraphQLScalarType;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.graphql.coercing.webmvc.UploadCoercing;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.graphql.server.WebGraphQlHandler;
import org.springframework.graphql.server.webmvc.GraphQlHttpHandler;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

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

    // TODO add to Spring Boot
    public static final List<MediaType> SUPPORTED_RESPONSE_MEDIA_TYPES =
            Arrays.asList(MediaType.APPLICATION_GRAPHQL, MediaType.APPLICATION_JSON);
    @Bean
    @Order(1)
    public RouterFunction<ServerResponse> graphQlMultipartRouterFunction(
            GraphQlProperties properties,
            GraphQlHttpHandler graphQlHttpHandler
    ) {
        String path = properties.getPath();
        RouterFunctions.Builder builder = RouterFunctions.route();
        builder = builder.POST(path, RequestPredicates.contentType(MULTIPART_FORM_DATA)
                .and(RequestPredicates.accept(SUPPORTED_RESPONSE_MEDIA_TYPES.toArray(MediaType[]::new))), graphQlHttpHandler::handleMultipartRequest);
        return builder.build();
    }
}
