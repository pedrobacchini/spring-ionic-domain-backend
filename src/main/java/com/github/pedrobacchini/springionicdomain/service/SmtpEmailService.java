package com.github.pedrobacchini.springionicdomain.service;

import com.github.pedrobacchini.springionicdomain.config.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import javax.mail.internet.MimeMessage;

/*
Padrão de projeto Strategy:
Classe ConcreteStrategy que define a estratégia para quando o contexto de execução for de desenvolvimento.
 */
@Service
@Profile("dev")
public class SmtpEmailService extends AbstractEmailService {

    private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

    private final MailSender mailSender;
    private final JavaMailSender javaMailSender;

    public SmtpEmailService(TemplateEngine templateEngine,
                            MailSender mailSender,
                            JavaMailSender javaMailSender,
                            ApplicationProperties applicationProperties) {
        super(templateEngine, javaMailSender, applicationProperties);
        this.mailSender = mailSender;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(SimpleMailMessage simpleMailMessage) {
        LOG.info("Enviando email...");
        mailSender.send(simpleMailMessage);
        LOG.info("Email enviado");
    }

    @Override
    public void sendHtmlEmail(MimeMessage mimeMessage) {
        LOG.info("Enviando email html...");
        javaMailSender.send(mimeMessage);
        LOG.info("Email enviado");
    }
}
