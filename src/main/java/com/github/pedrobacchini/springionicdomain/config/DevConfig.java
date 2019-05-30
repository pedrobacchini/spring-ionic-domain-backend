package com.github.pedrobacchini.springionicdomain.config;

import com.github.pedrobacchini.springionicdomain.service.DBService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("dev")
public class DevConfig {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    private final DBService dbService;

    public DevConfig(DBService dbService) { this.dbService = dbService; }

    @Bean
    public void instantiateDatabase() throws ParseException {

        if("create".equals(strategy))
            dbService.instantiateTestDatabase();
    }

}
