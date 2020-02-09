package com.github.pedrobacchini.springionicdomain;

import com.github.pedrobacchini.springionicdomain.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class SpringIonicDomainApplication {

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(SpringIonicDomainApplication.class, args);
    }

    public static <T> T getBean(Class<T> type) {
        assert applicationContext != null;
        return applicationContext.getBean(type);
    }
}
