package com.hcltech.shopease.security;

import com.auth0.jwt.JWT; import com.auth0.jwt.algorithms.Algorithm; import com.auth0.jwt.interfaces.DecodedJWT; import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Component;

import java.util.Date;

@Component public class JwtUtil { @Value("${jwt.secret}") private String jwtSecret;

    @Value("${jwt.expiration-ms}")
    private Long jwtExpirationMs;

    public String generateToken(String username, String role){
        Algorithm algo = Algorithm.HMAC256(jwtSecret);
        return JWT.create()
                .withSubject(username)
                .withClaim("role", role)
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .sign(algo);
    }

    public DecodedJWT validateTokenAndGet(String token){
        Algorithm algo = Algorithm.HMAC256(jwtSecret);
        return JWT.require(algo).build().verify(token);
    }

}
