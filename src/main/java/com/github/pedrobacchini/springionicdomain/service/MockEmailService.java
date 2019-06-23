package com.github.pedrobacchini.springionicdomain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/*
Padrão de projeto Strategy:
Classe ConcreteStrategy que define a estratégia para quando o contexto de executção for de testes.
 */
@Service
@Profile("test")
public class MockEmailService extends AbstractEmailService {

    private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

    @Override
    public void sendEmail(SimpleMailMessage simpleMailMessage) {
        LOG.info("Simulando envio de email");
        LOG.info(simpleMailMessage.toString());
        LOG.info("Email enviado");
    }
}
