package com.sgi.user.service;

import reactor.core.publisher.Mono;

public interface TokenProvider {

    Mono<String> createToken(Object user, String username);
}
