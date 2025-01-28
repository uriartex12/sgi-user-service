package com.sgi.user.repository;

import com.sgi.user.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepositoryJpa extends ReactiveMongoRepository<User, String> {

    Mono<Boolean> existsByUsername(String username);

    Mono<User> findByUsernameAndPasswordAndState(String username, String password, String state);

}
