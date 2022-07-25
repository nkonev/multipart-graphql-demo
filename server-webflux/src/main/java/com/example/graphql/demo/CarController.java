package com.example.graphql.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CarController {

    private static final Logger logger = LoggerFactory.getLogger(CarController.class);

    @QueryMapping(name = "cars")
    public List<Car> getCars() {
        logger.info("Getting cars");

        return List.of(new Car(1, "Reactive BMW"), new Car(2, "Reactive Toyota"));
    }

}

record Car(Integer id, String name) {}
