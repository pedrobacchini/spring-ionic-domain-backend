package com.github.pedrobacchini.springionicdomain.resource;

import com.github.pedrobacchini.springionicdomain.security.ClientUserDetails;
import com.github.pedrobacchini.springionicdomain.security.JWTUtil;
import com.github.pedrobacchini.springionicdomain.service.UserService;
import com.github.pedrobacchini.springionicdomain.service.exception.AuthorizationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthResource {

    private final JWTUtil jwtUtil;

    @PostMapping("/refresh_token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        Optional<ClientUserDetails> clientUserDetails = UserService.authenticated();
        if (!clientUserDetails.isPresent())
            throw new AuthorizationException("Acesso Negado");
        String token = jwtUtil.generateToken(clientUserDetails.get().getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        return ResponseEntity.noContent().build();
    }
}
