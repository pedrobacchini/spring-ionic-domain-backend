package com.github.pedrobacchini.springionicdomain.resource;

import com.github.pedrobacchini.springionicdomain.config.LocaleMessageSource;
import com.github.pedrobacchini.springionicdomain.dto.EmailDTO;
import com.github.pedrobacchini.springionicdomain.security.ClientUserDetails;
import com.github.pedrobacchini.springionicdomain.security.JWTUtil;
import com.github.pedrobacchini.springionicdomain.service.AuthService;
import com.github.pedrobacchini.springionicdomain.service.UserService;
import com.github.pedrobacchini.springionicdomain.service.exception.AuthorizationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthResource {

    private final JWTUtil jwtUtil;
    private final AuthService authService;
    private final LocaleMessageSource localeMessageSource;

    @PostMapping("/refresh-token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        Optional<ClientUserDetails> authenticated = UserService.authenticated();
        if (!authenticated.isPresent())
            throw new AuthorizationException(localeMessageSource.getMessage("access-denied"));
        String token = jwtUtil.generateToken(authenticated.get().getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/forgot")
    public ResponseEntity<Void> forgot(@RequestBody @Valid EmailDTO emailDTO) {
        authService.sendNewPassword(emailDTO.getEmail());
        return ResponseEntity.noContent().build();
    }
}
