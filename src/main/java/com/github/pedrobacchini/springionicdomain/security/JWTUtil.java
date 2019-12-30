package com.github.pedrobacchini.springionicdomain.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    String secret;

    @Value("${jwt.expiration}")
    Long expiration;

    public boolean tokenValid(String token) {
        return getClaims(token).map(claims -> {
            String username = claims.getSubject();
            Date expiration = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            return username != null && expiration != null && now.before(expiration);
        }).orElse(false);
    }

    public String getUsername(String token) { return getClaims(token).map(Claims::getSubject).orElse(null); }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }

    private Optional<Claims> getClaims(String token) {
        try {
            return Optional.of(Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
