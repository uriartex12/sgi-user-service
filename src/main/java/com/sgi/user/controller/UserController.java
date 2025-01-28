package com.sgi.user.controller;

import com.sgi.controller.V1Api;
import com.sgi.dto.AuthenticationRequest;
import com.sgi.dto.AuthenticationResponse;
import com.sgi.dto.UserRequest;
import com.sgi.dto.UserResponse;
import com.sgi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class UserController implements V1Api {

    private final UserService userService;

    @Override
    public Mono<ResponseEntity<AuthenticationResponse>> authentication(Mono<AuthenticationRequest> authenticationRequest, ServerWebExchange exchange) {
        return userService.loadUserByUsernameAndPassword(authenticationRequest)
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<UserResponse>> createUser(Mono<UserRequest> userRequest, ServerWebExchange exchange) {
        return userService.createUser(userRequest)
                .map(ResponseEntity::ok);
    }
}
