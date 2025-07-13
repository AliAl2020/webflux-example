package com.appsdeveoperblog.reactive.ws.demo.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, UUID> {
            Flux<UserEntity> findAllBy(Pageable pageable);
}
