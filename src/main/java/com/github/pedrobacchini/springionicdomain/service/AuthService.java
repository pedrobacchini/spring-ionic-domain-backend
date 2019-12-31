package com.github.pedrobacchini.springionicdomain.service;

import com.github.pedrobacchini.springionicdomain.domain.Cliente;
import com.github.pedrobacchini.springionicdomain.repository.ClienteRepository;
import com.github.pedrobacchini.springionicdomain.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final EmailService emailService;
    private final ClienteRepository clienteRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Random random = new Random();

    public void sendNewPassword(String email) {
        Optional<Cliente> optionalCliente = clienteRepository.findByEmail(email);
        if (!optionalCliente.isPresent())
            throw new ObjectNotFoundException("Email não encontrado");
        String newPass = newPassword();
        optionalCliente.get().setSenha(bCryptPasswordEncoder.encode(newPass));
        clienteRepository.save(optionalCliente.get());
        emailService.sendNewPasswordEmail(optionalCliente.get(), newPass);
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
