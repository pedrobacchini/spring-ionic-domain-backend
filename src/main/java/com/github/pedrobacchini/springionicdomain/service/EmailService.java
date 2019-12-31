package com.github.pedrobacchini.springionicdomain.service;

import com.github.pedrobacchini.springionicdomain.domain.Cliente;
import com.github.pedrobacchini.springionicdomain.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

/*
Padrão de projeto Strategy:
Classe Strategy que define como deve ser os metodos, a serem implementados
por cada classe ConcreteStrategy.
 */
public interface EmailService {

    void sendOrderConfirmationEmail(Pedido pedido);

    void sendEmail(SimpleMailMessage simpleMailMessage);

    void sendNewPasswordEmail(Cliente cliente, String newPass);

    void sendHtmlEmail(MimeMessage mimeMessage);

    void sendOrderConfirmationHtmlEmail(Pedido pedido);
}
