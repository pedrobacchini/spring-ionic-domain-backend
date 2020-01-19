package com.github.pedrobacchini.springionicdomain.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

public class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException {
        httpServletResponse.setStatus(401);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.getWriter().append(json());
    }

    private String json() {
        return "{\"timestamp\": " + Instant.now().toEpochMilli() + ", "
                + "\"status\":401, "
                + "\"error\":\"Não autorizado\", "
                + "\"message\":\"Email ou senha inválidos\", "
                + "\"path\":\"/login\"}";
    }
}
