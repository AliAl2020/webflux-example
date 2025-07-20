package com.appsdeveoperblog.reactive.ws.demo.service;


import com.appsdeveoperblog.reactive.ws.demo.data.UserEntity;
import com.appsdeveoperblog.reactive.ws.demo.data.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final ReactiveAuthenticationManager reactiveAuthenticationManager;
    private final UserRepository userRepository;

    @Override
    public Mono<Map<String, String>> authenticate(String username, String password) {
        return reactiveAuthenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username,password))
                .then(getUserDetails(username))
                .map(this::createAuthRespose);
    }

    private Mono<UserEntity> getUserDetails(String username)
    {
        return userRepository.findByEmail(username);
    }

    private Map<String,String> createAuthRespose(UserEntity user){

        Map<String, String> result = new HashMap<>();
        result.put("userId",user.getId().toString());
        result.put("token","JWT");
        return result;
    }

}
