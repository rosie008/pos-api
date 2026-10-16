package com.rose.pos_api.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class AuthService {

    private final SecretKey signingKey;
    private final long expirationMillis = 15 * 60 * 1000;
    private final String issuer = "https://auth.rose.com";
    private final String audience = "https://api.rose.come";


    public AuthService(@Value("${JWT_SECRET}") String secretBase64) {
        byte[] keyBytes = Decoders.BASE64.decode(secretBase64);
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String userId, String role) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .subject(userId)
                .claim("role", role)
                .issuer(issuer)
                .audience().add(audience).and()
                .issuedAt(now)
                .expiration(expiry)
                .signWith(signingKey)
                .compact();
    }

    public Claims validateAndGetClaims(String token){
        return  Jwts.parser()
                .verifyWith(signingKey)
                .requireIssuer(issuer)
                .requireAudience(audience)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
