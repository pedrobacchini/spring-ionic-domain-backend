package com.github.pedrobacchini.springionicdomain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

/*
Padrão de projeto Strategy:
Classe ConcreteStrategy que define a estratégia para quando o contexto de executção for de testes.
 */
public class MockEmailService extends AbstractEmailService {

    private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

    @Override
    public void sendEmail(SimpleMailMessage simpleMailMessage) {
        LOG.info("Simulando envio de email");
        LOG.info(simpleMailMessage.toString());
        LOG.info("Email enviado");
    }
}
