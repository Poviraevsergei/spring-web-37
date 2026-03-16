package com.tms.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(String username) {
        log.info("IN JwtUtils:generateToken");
        String jwt = Jwts.builder()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(expiration)))
                .issuedAt(new Date(System.currentTimeMillis()))
                .signWith(getSigningKey())
                .compact();
        log.info("OUT JwtUtils:generateToken");
        return jwt;
    }

    private Key getSigningKey() {
        log.info("IN JwtUtils:getSigningKey");
        byte[] keyBytes = this.secret.getBytes(StandardCharsets.UTF_8);
        Key resultKey = Keys.hmacShaKeyFor(keyBytes);
        log.info("OUT JwtUtils:getSigningKey");
        return resultKey;
    }

    public Optional<String> getTokenFromHttpRequest(ServletRequest servletRequest) {
        HttpServletRequest request = ((HttpServletRequest) servletRequest);
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return Optional.of(bearerToken.substring(7));
        }
        return Optional.empty();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
