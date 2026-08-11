package com.devtrack.backend_java.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtils {

    private final SecretKey key = Jwts.SIG.HS256.key().build();
    private final long EXPIRATION_MS = 1000 * 60 * 60 * 1;

    public String generateToken(String email){
        return Jwts.builder().subject(email).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + EXPIRATION_MS)).signWith(key).compact();
    }

    public String extractEmail(String token){
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public Boolean isTokenValid(String token){

        try {Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        }catch(JwtException | IllegalArgumentException ex){
            return false;
        }        
    }

}
