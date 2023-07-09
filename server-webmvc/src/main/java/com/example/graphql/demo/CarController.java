package com.example.graphql.demo;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.RandomStringUtils;
import org.dataloader.DataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.graphql.execution.BatchLoaderRegistry;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import java.time.temporal.ChronoUnit;
import java.security.Principal;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class CarController {

    private static final Logger logger = LoggerFactory.getLogger(CarController.class);

    public CarController(BatchLoaderRegistry registry, UserDataLoader userDataLoader) {
        registry.forTypePair(Integer.class, User.class).registerMappedBatchLoader(userDataLoader);
    }

    @QueryMapping(name = "cars")
    public List<Car> getCars() {
        logger.info("Getting cars");

        return List.of(new Car(1, "BMW Webmvc", 1), new Car(2, "Toyota Webmvc", 2));
    }

    @SchemaMapping(typeName = "ExtendedCar", field = "owner")
    public CompletableFuture<User> ownerMapping(Car car, DataLoader<Integer, User> loader) {
        logger.info("Resolving an owner for car {}", car);
        return loader.load(car.ownerId());
    }

//    @BatchMapping(typeName = "ExtendedCar", field = "owner")
//    public Mono<Map<Car, User>> author(List<Car> cars) {
//        //var ownerIds = cars.stream().map(Car::ownerId).toList();
//        Map<Car, User> map = new HashMap<>();
//        for (var car : cars) {
//            map.put(car, new User(2000+ car.id(), "Admin"+car.id()));
//        }
//        return Mono.just(map);
//    }


    @PreAuthorize("hasRole('ADMIN')")
    @QueryMapping(name = "secretCars")
    public List<Car> getSecretCars() {
        logger.info("Getting secret cars");

        return List.of(new Car(1, "Secret BMW", 1), new Car(2, "Secret Toyota", 2));
    }

    @SubscriptionMapping(name = "carEvents")
    public Flux<Car> getCarFlux() {
        logger.info("Getting car events");

        var counter = new AtomicInteger();
        Flux<Car> flux = Flux.generate(synchronousSink -> {
            synchronousSink.next(new Car(counter.getAndIncrement(), RandomStringUtils.randomAscii(3, 10), 1));
        });
        Flux<Car> carFlux = flux.delayElements(Duration.of(1, ChronoUnit.SECONDS));
        return carFlux;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @SubscriptionMapping(name = "secretCarEvents")
    public Flux<Car> getSecretCarFlux(Principal principal) {
        logger.info("Getting secret car events");

        SecurityContext context = SecurityContextHolder.getContext();
        var counter = new AtomicInteger();
        Flux<Car> flux = Flux.generate(synchronousSink -> {
            synchronousSink.next(new Car(counter.getAndIncrement(), "Top Secret Car " + RandomStringUtils.randomAscii(3, 4)  + " owned by " + principal.getName() + ", authentication from context is " + context.getAuthentication(), 2));
        });
        Flux<Car> carFlux = flux.delayElements(Duration.of(1, ChronoUnit.SECONDS));
        return carFlux;
    }

    @PostConstruct
    public void pc() {
        var logger = LoggerFactory.getLogger(CarController.class);
//        this.getCarFlux()
//            .subscribe(car -> {
//                logger.info("A car generated {}", car);
//            });
    }

    @MutationMapping("carMutation")
    public CarInput carMutation(@Argument("input") @Valid CarInput car) {
        logger.info("Mutating {}", car);

        return car;
    }
}

record CarInput(@NotNull Integer id, @NotEmpty String name) {}

record Car(Integer id, String name, Integer ownerId) {}

record User(Integer id, String name) {}
