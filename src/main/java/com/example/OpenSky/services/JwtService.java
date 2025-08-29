package com.example.OpenSky.services;

import com.example.OpenSky.entities.Session;
import com.example.OpenSky.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.refresh}")
    private String refresh;

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    public String generateAccessToken(Session session) {
        UserRole role = session.getUser().getRole();
        return Jwts.builder()
                .setSubject(session.getId().toString())
                .claim("id",session.getUser().getId())
                .claim("role","ROLE_"+ role.name().toUpperCase())
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .compact();

    }

    public String generateRefreshToken(Session session) {
        return Jwts.builder()
                .setSubject(session.getId().toString())
                .signWith(Keys.hmacShaKeyFor(refresh.getBytes()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .compact();
    }

    public Claims parseAccessToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
