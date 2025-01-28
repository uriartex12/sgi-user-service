package com.sgi.user.service;

import com.sgi.dto.AuthenticationRequest;
import com.sgi.dto.AuthenticationResponse;
import com.sgi.dto.UserRequest;
import com.sgi.dto.UserResponse;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<UserResponse> createUser(Mono<UserRequest> userRequestMono);

    Mono<AuthenticationResponse> loadUserByUsernameAndPassword(Mono<AuthenticationRequest> authenticationRequestMono);

}
