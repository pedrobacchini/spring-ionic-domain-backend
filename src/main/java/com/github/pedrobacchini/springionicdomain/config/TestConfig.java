package com.github.pedrobacchini.springionicdomain.config;

import com.github.pedrobacchini.springionicdomain.service.DBService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("test")
@RequiredArgsConstructor
public class TestConfig {

    private final DBService dbService;

    @Bean
    public boolean instantiateDatabase() throws ParseException {
        dbService.instantiateTestDatabase();
        return true;
    }
}
