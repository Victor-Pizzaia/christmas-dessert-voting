package com.christmas.dessert.voting.christmas_dessert_voting.infra;

import java.util.Date;

import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Component
public class JwtProvider {

    @Value("${security.jwt.encryption_key}")
    private String encryptionKey;
    @Value("${security.jwt.expiration_time}")
    private long expirationTime;

    private SecretKey getSecurityKey() {
        byte[] base64Key = Decoders.BASE64.decode(encryptionKey);
        return Keys.hmacShaKeyFor(base64Key);
    }

    public String generateToken(String userId, String identifier) {
        return Jwts.builder()
                .subject(userId)
                .claim("identifier", identifier)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSecurityKey())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getSecurityKey()).build().parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String getUserIdFromJwt(String token) {
        return Jwts.parser()
                .verifyWith(getSecurityKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public long getExpiresIn() {
        return expirationTime / 1000;
    }
}
