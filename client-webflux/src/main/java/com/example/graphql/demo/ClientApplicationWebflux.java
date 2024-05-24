package com.example.graphql.demo;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ClientApplicationWebflux {

	public static void main(String[] args) {
        new SpringApplicationBuilder(ClientApplicationWebflux.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

}
