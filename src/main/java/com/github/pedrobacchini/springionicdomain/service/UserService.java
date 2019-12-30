package com.github.pedrobacchini.springionicdomain.service;

import com.github.pedrobacchini.springionicdomain.security.ClientUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class UserService {

    public static Optional<ClientUserDetails> authenticated() {
        return Optional.of((ClientUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
