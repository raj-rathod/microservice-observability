package com.rajesh.microservices.user.component;

import java.util.Date;
import java.util.UUID;
import org.springframework.stereotype.Component;

import com.rajesh.microservices.user.entity.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

@Component
public class JwtUtil {
    
    private final Key key = Keys.hmacShaKeyFor("mysecretkeymysecretkeymysecretkey".getBytes());

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", user.getId())
                .claim("name", user.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
                .signWith(key) // ✅ updated
                .compact();
    }

    public String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }
}
