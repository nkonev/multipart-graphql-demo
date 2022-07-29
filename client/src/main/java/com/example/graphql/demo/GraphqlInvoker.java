package com.example.graphql.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.graphql.client.ClientGraphQlResponse;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class GraphqlInvoker implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphqlInvoker.class);


    @Autowired
    private HttpGraphQlClient httpGraphQlClient;

    //@Override
    public void run(String... args) throws Exception {
        LOGGER.info("Hello");
        var doc = """
                mutation FileNUpload($files: [Upload!]) {multiFileUpload(files: $files){id}}
                """;
        Mono<ClientGraphQlResponse> execute = httpGraphQlClient
                .document(doc)
                .fileVariable("files", List.of(new ClassPathResource("/foo.txt"), new ClassPathResource("/bar.txt")))
                .executeFileUpload();
        execute.block();
    }

    public void runOld(String... args) throws Exception {
        LOGGER.info("Hello");
        var doc = """
                mutation FileUpload($file: Upload!) {fileUpload(file: $file){id}}
                """;
        Mono<ClientGraphQlResponse> execute = httpGraphQlClient
                .document(doc)
                .fileVariable("file", new ClassPathResource("/foo.txt"))
                .executeFileUpload();
        execute.block();
    }
}
