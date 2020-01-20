package com.github.pedrobacchini.springionicdomain.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Component
public class LocaleMessageSource {

    private static final Locale PT_BR_LOCALE = new Locale("pt", "BR");
    private static final Locale US_LOCALE = Locale.US;

    private final Locale defaultLocale;
    private final MessageSource messageSource;

    public LocaleMessageSource(MessageSource messageSource, ApplicationProperties applicationProperties) {
        this.defaultLocale = applicationProperties.getLocale().equals("pt_BR") ? PT_BR_LOCALE : US_LOCALE;
        this.messageSource = messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(defaultLocale);
        return slr;
    }

    public String getMessage(String code) { return messageSource.getMessage(code, null, defaultLocale); }

    public String getMessage(String code, Object... args) { return messageSource.getMessage(code, args, defaultLocale); }
}
