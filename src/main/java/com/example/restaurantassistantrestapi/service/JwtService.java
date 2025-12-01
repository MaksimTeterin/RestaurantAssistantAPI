package com.example.restaurantassistantrestapi.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    @Value("${JWT_SECRET}")
    private String jwtSecret;

    public String generateUserToken(String email) {

        long now = System.currentTimeMillis();
        long expiration = now + 1000 * 60 * 30; // 30 minutes

        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
