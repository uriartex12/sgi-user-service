package com.sgi.user.service.impl;
import com.sgi.user.service.TokenProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class JwtService implements TokenProvider {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token-expiration-seconds}")
    private Long tokenExpiration;


    public Mono<String> createToken(Object user, String username) {
        long expirationTime = tokenExpiration * 1000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        return Mono.just(Jwts.builder()
                .claim("User", user)
                .setSubject(username)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact());
    }

}
