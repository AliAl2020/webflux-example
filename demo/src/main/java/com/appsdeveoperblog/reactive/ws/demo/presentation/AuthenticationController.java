package com.appsdeveoperblog.reactive.ws.demo.presentation;


import com.appsdeveoperblog.reactive.ws.demo.presentation.model.AuthenticationRequest;
import com.appsdeveoperblog.reactive.ws.demo.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public Mono<ResponseEntity<Void>> login(@RequestBody Mono<AuthenticationRequest> authenticationRequestMono){

        return authenticationRequestMono
                .flatMap(authenticationRequest ->
                authenticationService.authenticate(authenticationRequest.getEmail(),authenticationRequest.getPassword()))
                .map(authenticationResultMap -> ResponseEntity.ok()
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authenticationResultMap.get("token"))
                        .header("UserId",authenticationResultMap.get("userId"))
                        .build());


    }
}
