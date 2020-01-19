package com.github.pedrobacchini.springionicdomain.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring-ionic-domain")
public class ApplicationProperties {

    @Getter
    private final Email email = new Email();

    @Getter
    @Setter
    public static class Email {
        private String defaultSender;
        private String defaultRecipient;
    }

    @Getter
    private final JWT jwt = new JWT();

    @Getter
    @Setter
    public static class JWT {
        private String secret;
        private String expiration;
    }

    @Getter
    private final AWS aws = new AWS();

    @Getter
    @Setter
    public static class AWS {
        private String accessKeyId;
        private String secretAccessKey;
    }

    @Getter
    private final S3 s3 = new S3();

    @Getter
    @Setter
    public static class S3 {
        private String bucket;
        private String region;
    }
}
