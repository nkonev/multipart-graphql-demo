package com.example.graphql.demo;

import org.dataloader.BatchLoaderEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

@Component
public class UserDataLoader implements BiFunction<Set<Integer>, BatchLoaderEnvironment, Mono<Map<Integer, User>>> {

    private static final Logger logger = LoggerFactory.getLogger(UserDataLoader.class);

    @Override
    public Mono<Map<Integer, User>> apply(Set<Integer> ownerIds, BatchLoaderEnvironment batchLoaderEnvironment) {
        logger.info("Loading using dataloader");

        Map<Integer, User> map = new HashMap<>();
        for (var id : ownerIds) {
            map.put(id, new User(2000+id, "Admin"+id));
        }
        return Mono.just(map);
    }
}
