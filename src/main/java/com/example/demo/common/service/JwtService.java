package com.example.demo.common.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;

@Service
public class JwtService {

    private final String JWT_SECRET_KEY = "hagsdfasdfqqy87286183yeuahshq28711";

    public String generateJwtToken(UserDetails userDetails){
        return JWT.create()
                .withExpiresAt(Instant.now().plus(15, ChronoUnit.MINUTES))
                .withIssuedAt(Instant.now())
                .withSubject(userDetails.getUsername())
                .withIssuer("hanuvi@org")
                .withClaim("roles", getRolesClaim(userDetails))
                .sign(Algorithm.HMAC512(JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8)));
    }


    private List<String> getRolesClaim(UserDetails userDetails) {
        return getRolesClaim(userDetails.getAuthorities());
    }

    private List<String> getRolesClaim(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().map(GrantedAuthority::getAuthority).toList();
    }

    public boolean validateToken(String jwtToken) {
        if (!jwtToken.startsWith("Bearer ")) return false;
        jwtToken = jwtToken.substring("Bearer ".length());
        return !getExpirationDate(jwtToken).isBefore(Instant.now());
    }

    public String getUsername(String jwtToken) {
        return JWT.decode(jwtToken).getSubject();
    }

    public Instant getExpirationDate(String jwtToken) {
        return JWT.decode(jwtToken).getExpiresAtAsInstant();
    }

    private DecodedJWT getDecodedJwt(String jwtToken){
        return JWT.require(Algorithm.HMAC512(JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                .build()
                .verify(jwtToken);


    }

}
