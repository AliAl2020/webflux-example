package com.appsdeveoperblog.reactive.ws.demo.service;

import reactor.core.publisher.Mono;

import java.util.Map;

public interface AuthenticationService {
    Mono<Map<String,String>> authenticate(String username, String password);
}
