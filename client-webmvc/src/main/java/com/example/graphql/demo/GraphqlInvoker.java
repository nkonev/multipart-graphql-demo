package com.example.graphql.demo;

import name.nkonev.multipart.spring.graphql.client.support.MultipartClientGraphQlRequest;
import name.nkonev.multipart.spring.graphql.client.webmvc.MultipartGraphQlRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static java.util.Collections.singletonMap;

@Component
public class GraphqlInvoker implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphqlInvoker.class);


    @Autowired
    private MultipartGraphQlRestClient httpGraphQlClient;

    @Override
    public void run(String... args) {
        LOGGER.info("Hello");
        var doc = """
                mutation FileNUpload($files: [Upload!]) {multiFileUpload(files: $files){id}}
                """;
        java.util.Map<String, Object> fileVariables = singletonMap("files", Arrays.asList(new ClassPathResource("/foo.txt"), new ClassPathResource("/bar.txt")));
        var request = MultipartClientGraphQlRequest.builder()
            .withDocument(doc)
            .withFileVariables(fileVariables)
            .build();
        var response = httpGraphQlClient.executeFileUpload("http://localhost:8889/graphql", request);
        LOGGER.info("Response is {}", response);
    }

}
