package com.github.pedrobacchini.springionicdomain.service;

import com.github.pedrobacchini.springionicdomain.config.LocaleMessageSource;
import com.github.pedrobacchini.springionicdomain.domain.Client;
import com.github.pedrobacchini.springionicdomain.repository.ClientRepository;
import com.github.pedrobacchini.springionicdomain.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final Random random = new Random();
    private final LocaleMessageSource localeMessageSource;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final EmailService emailService;

    private final ClientRepository clientRepository;

    public void sendNewPassword(String email) {
        Optional<Client> optionalClient = clientRepository.findByEmail(email);
        if (!optionalClient.isPresent())
            throw new ObjectNotFoundException(localeMessageSource.getMessage("email-not-found"));
        String newPass = newPassword();
        optionalClient.get().setPassword(bCryptPasswordEncoder.encode(newPass));
        clientRepository.save(optionalClient.get());
        emailService.sendNewPasswordEmail(optionalClient.get(), newPass);
    }

    private String newPassword() {
        char[] vet = new char[10];
        for (int i = 0; i < vet.length; i++)
            vet[i] = randomChar();
        return new String(vet);
    }

    private char randomChar() {
        int opt = random.nextInt(3);
        switch (opt) {
            case 0: //gera um numero
                return (char) (random.nextInt(10) + 48);
            case 1: //gera uma letra maiscula
                return (char) (random.nextInt(26) + 65);
            default: //gera uma letra minuscula
                return (char) (random.nextInt(26) + 97);
        }
    }
}
