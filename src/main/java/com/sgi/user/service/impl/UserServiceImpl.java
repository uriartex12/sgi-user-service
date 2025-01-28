package com.sgi.user.service.impl;

import com.sgi.dto.AuthenticationRequest;
import com.sgi.dto.AuthenticationResponse;
import com.sgi.dto.UserRequest;
import com.sgi.dto.UserResponse;
import com.sgi.user.enums.UserState;
import com.sgi.user.mapper.UserMapper;
import com.sgi.user.model.User;
import com.sgi.user.repository.UserRepositoryJpa;
import com.sgi.user.service.TokenProvider;
import com.sgi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;

import static com.sgi.user.util.Util.hashPassword;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepositoryJpa repositoryJpa;

    private final TokenProvider tokenProvider;

    @Override
    public Mono<UserResponse> createUser(Mono<UserRequest> userRequestMono) {
        return userRequestMono.flatMap(userRequest ->
                repositoryJpa.existsByUsername(userRequest.getUsername())
                        .filter(userExists -> !userExists)
                        .switchIfEmpty(Mono.error(new Exception("Username already exists")))
                        .flatMap(userExists -> {
                                User user = User.builder()
                                        .username(userRequest.getUsername())
                                        .password(hashPassword(userRequest.getPassword()))
                                        .clientId(userRequest.getClientId())
                                        .state(UserState.ACTIVE.name())
                                        .createdDate(Instant.now())
                                        .updatedDate(Instant.now())
                                        .build();
                                return repositoryJpa.save(user)
                                        .map(UserMapper.INSTANCE::map);
                        })
        );
    }

    @Override
    public Mono<AuthenticationResponse> loadUserByUsernameAndPassword(Mono<AuthenticationRequest> authenticationRequestMono) {
        return authenticationRequestMono.flatMap(authenticationRequest ->
                repositoryJpa.findByUsernameAndPasswordAndState(authenticationRequest.getUsername(),
                                hashPassword(authenticationRequest.getPassword()), UserState.ACTIVE.name())
                        .switchIfEmpty(Mono.error(new Exception("Username or password incorrect or user is inactive")))
                        .flatMap(user -> {
                            return tokenProvider.createToken(user, user.getUsername())
                                    .flatMap(token -> {
                                        return Mono.just(UserMapper.INSTANCE.map(user, token));
                                    });

                        })
        );
    }
}
