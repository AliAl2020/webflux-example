package com.appsdeveoperblog.reactive.ws.demo.presentation.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest {
    @NotBlank(message="First Name cannot be empty")
    @Size(min = 2,max = 50, message="firstname cannot be shorter than 2 or longer than 50")
    private String firstName;
    @NotBlank(message="last Name cannot be empty")
    @Size(min = 2,max = 50, message="lastname cannot be shorter than 2 or longer than 50")
    private String lastName;
    @NotBlank(message="email cannot be empty")
    @Email(message=" please enter valid email")
    private String email;
    @NotBlank(message="password cannot be empty")
    @Size(min = 8,max = 16, message="password cannot be shorter than 8 or longer than 16")
    private String password;



}
