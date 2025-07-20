package com.appsdeveoperblog.reactive.ws.demo.presentation.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthenticationRequest {

    private String email;
    private String password;


}
