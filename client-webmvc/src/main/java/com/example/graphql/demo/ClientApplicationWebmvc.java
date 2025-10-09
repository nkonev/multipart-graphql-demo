package com.example.graphql.demo;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ClientApplicationWebmvc {

	public static void main(String[] args) {
        new SpringApplicationBuilder(ClientApplicationWebmvc.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

}
