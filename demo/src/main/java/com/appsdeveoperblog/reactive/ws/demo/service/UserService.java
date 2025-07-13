package com.appsdeveoperblog.reactive.ws.demo.service;

import com.appsdeveoperblog.reactive.ws.demo.presentation.CreateUserRequest;
import com.appsdeveoperblog.reactive.ws.demo.presentation.UserRest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserService {

    Mono<UserRest> createUser(Mono<CreateUserRequest> createUserRequestMono);
    Mono<UserRest> getUserById(UUID id);
    Flux<UserRest> findAll(int page, int limit);
}
