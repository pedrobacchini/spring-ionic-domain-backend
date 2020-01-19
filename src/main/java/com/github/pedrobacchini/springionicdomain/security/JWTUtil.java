package com.github.pedrobacchini.springionicdomain.security;

import com.github.pedrobacchini.springionicdomain.config.ApplicationProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JWTUtil {

    private final ApplicationProperties applicationProperties;

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
                .setExpiration(new Date(System.currentTimeMillis() + applicationProperties.getJwt().getExpiration()))
                .signWith(SignatureAlgorithm.HS512, applicationProperties.getJwt().getSecret().getBytes())
                .compact();
    }

    private Optional<Claims> getClaims(String token) {
        try {
            return Optional.of(
                    Jwts.parser()
                            .setSigningKey(applicationProperties.getJwt().getSecret().getBytes())
                            .parseClaimsJws(token).getBody()
            );
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
