package com.github.pedrobacchini.springionicdomain;

import com.github.pedrobacchini.springionicdomain.config.ApplicationProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class SpringIonicDomainApplication implements CommandLineRunner {

    public static void main(String[] args) { SpringApplication.run(SpringIonicDomainApplication.class, args); }

    @Override
    public void run(String... args) { }
}
