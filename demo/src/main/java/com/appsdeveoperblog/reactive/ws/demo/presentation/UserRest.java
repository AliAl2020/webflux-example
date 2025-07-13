package com.appsdeveoperblog.reactive.ws.demo.presentation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserRest {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;


}
