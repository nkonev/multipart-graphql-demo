package com.example.graphql.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;

@Component
public class GraphqlInvoker implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphqlInvoker.class);


    @Autowired
    private HttpGraphQlClient httpGraphQlClient;

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Hello");
        // httpGraphQlClient.mutate();
    }
}
