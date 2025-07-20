package com.appsdeveoperblog.reactive.ws.demo.service;

import com.appsdeveoperblog.reactive.ws.demo.data.UserEntity;
import com.appsdeveoperblog.reactive.ws.demo.data.UserRepository;
import com.appsdeveoperblog.reactive.ws.demo.presentation.model.CreateUserRequest;
import com.appsdeveoperblog.reactive.ws.demo.presentation.model.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Mono<UserRest> createUser(Mono<CreateUserRequest> createUserRequestMono) {
        return createUserRequestMono
                .flatMap(this::convertEntity) //createUserRequestMono --> cpnvert -->UserEntity
                .flatMap(userRepository::save) // convert to Mono
                .mapNotNull(this::convertToRest)
//                .onErrorMap(throwable-> {
//                    if (throwable instanceof DuplicateKeyException) {
//                        return new ResponseStatusException(HttpStatus.CONFLICT, throwable.getMessage());
//                    }
//                    else if (throwable instanceof DataIntegrityViolationException){
//                        return new ResponseStatusException(HttpStatus.BAD_REQUEST, throwable.getMessage());
//                    }
//                    else {
//                        return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, throwable.getMessage());
//                    }
//
//                })
                ;
    }

    @Override
    public Mono<UserRest> getUserById(UUID id) {
        return userRepository.findById(id).map(this::convertToRest);
    }

    @Override
    public Flux<UserRest> findAll(int page, int limit) {
        //if(page>0) page = page -1;
        Pageable pageable = PageRequest.of(page, limit);
        return userRepository.findAllBy(pageable)
                .map(this::convertToRest);
    }

    private Mono<UserEntity>convertEntity(CreateUserRequest createUserRequest){

        return Mono.fromCallable(()->{
            UserEntity userEntity=UserEntity.builder().build();
            BeanUtils.copyProperties(createUserRequest,userEntity);
            userEntity.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
            return userEntity;

        }).subscribeOn(Schedulers.boundedElastic());


    }

    private UserRest convertToRest(UserEntity userEntity){

        UserRest userRest = UserRest.builder().build();
        BeanUtils.copyProperties(userEntity,userRest);
        return userRest;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByEmail(username)
                .map(userEntity -> User
                        .withUsername(userEntity.getEmail())
                        .password(userEntity.getPassword())
                        .authorities(new ArrayList<>())
                        .build());
    }
}
