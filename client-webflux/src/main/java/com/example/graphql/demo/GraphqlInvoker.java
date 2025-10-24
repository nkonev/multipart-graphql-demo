package com.example.graphql.demo;

import name.nkonev.multipart.spring.graphql.client.support.MultipartClientGraphQlRequest;
import name.nkonev.multipart.spring.graphql.client.webflux.MultipartGraphQlWebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonMap;

@Component
public class GraphqlInvoker implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphqlInvoker.class);

    @Autowired
    private MultipartGraphQlWebClient httpGraphQlClient;

    @Override
    public void run(String... args) {
        LOGGER.info("Starting");
        var doc = """
                mutation FileNUpload($files: [Upload!]) {
                    multiFileUpload(files: $files){id}
                }
        """;
        Map<String, Object> fileVariables = singletonMap("files", List.of(new ClassPathResource("/foo.txt"), new ClassPathResource("/bar.txt")));

        var request = MultipartClientGraphQlRequest.builder()
            .withDocument(doc)
            .withFileVariables(fileVariables)
            .build();
        var response = httpGraphQlClient.executeFileUpload("http://localhost:8899/graphql", request).block();
        LOGGER.info("Response is {}", response);
    }

}
