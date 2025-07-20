package com.appsdeveoperblog.reactive.ws.demo.presentation;


import com.appsdeveoperblog.reactive.ws.demo.presentation.model.CreateUserRequest;
import com.appsdeveoperblog.reactive.ws.demo.presentation.model.UserRest;
import com.appsdeveoperblog.reactive.ws.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    //@ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping
    public Mono<ResponseEntity<UserRest>> createUser(@RequestBody @Valid Mono<CreateUserRequest> createUserRequest){

        //UserRest userrest = UserRest.builder().build();
        //return  Mono.just(userrest);

//        return createUserRequest.map(request -> {
//
//            return UserRest.builder()
//                    .id(UUID.randomUUID())
//                    .firstName(request.getFirstName())
//                    .lastName(request.getLastName())
//                    .email(request.getEmail())
//                    .build();
//
//        }).map(userRest -> ResponseEntity
//                .status(HttpStatus.CREATED)
//                .location(URI.create("/users/"+userRest.getId()))
//                .body(userRest));
        //--------------------------------------------------
        return userService.createUser(createUserRequest).map(item ->  ResponseEntity
                       .status(HttpStatus.CREATED)
                        .location(URI.create("/users/"+item.getId()))
                        .body(item));

    }

    @GetMapping("/{userId}")
    public Mono<ResponseEntity<UserRest>> getUser(@PathVariable("userId") UUID userId){

//return Mono.just(UserRest.builder()
//                .id(UUID.randomUUID())
//                .firstName("sergey")
//                .lastName("baalbkai")
//                .email("ali@gmail.com")
//
//        .build());
//-------------------------------------------------------------------
        return userService.getUserById(userId)
                .map(item->ResponseEntity.status(HttpStatus.OK).body(item))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build()));

    }

    @GetMapping("/all")
    public Flux<UserRest> getUsers(@RequestParam(value="page",defaultValue = "0")int page,
                                   @RequestParam(value="limit", defaultValue="50") int limit){

//        return Flux.just(UserRest.builder().id(UUID.randomUUID()).firstName("sergey").lastName("baalbkai").email("ali@gmail.com").build(),
//                        UserRest.builder().id(UUID.randomUUID()).firstName("sergey1").lastName("baalbkai1").email("ali1@gmail.com").build()
//                        );


        return userService.findAll(page,limit);
    }

}
