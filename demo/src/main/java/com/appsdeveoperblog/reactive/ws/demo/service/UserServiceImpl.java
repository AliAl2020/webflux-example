package com.appsdeveoperblog.reactive.ws.demo.service;

import com.appsdeveoperblog.reactive.ws.demo.data.UserEntity;
import com.appsdeveoperblog.reactive.ws.demo.data.UserRepository;
import com.appsdeveoperblog.reactive.ws.demo.presentation.CreateUserRequest;
import com.appsdeveoperblog.reactive.ws.demo.presentation.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public Mono<UserRest> createUser(Mono<CreateUserRequest> createUserRequestMono) {
        return createUserRequestMono
                .mapNotNull(this::convertEntity) //createUserRequestMono --> cpnvert -->UserEntity
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

    private UserEntity convertEntity(CreateUserRequest createUserRequest){

        UserEntity userEntity=UserEntity.builder().build();
        BeanUtils.copyProperties(createUserRequest,userEntity);
        return userEntity;
    }

    private UserRest convertToRest(UserEntity userEntity){

        UserRest userRest = UserRest.builder().build();
        BeanUtils.copyProperties(userEntity,userRest);
        return userRest;
    }
}
