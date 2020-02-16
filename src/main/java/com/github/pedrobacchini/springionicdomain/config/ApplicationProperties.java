package com.github.pedrobacchini.springionicdomain.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring-ionic-domain")
public class ApplicationProperties {

    @Getter
    @Setter
    private String locale;

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
        private Long expiration;
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

        public String getBucketBaseUrl() { return "https://"+bucket+".s3.amazonaws.com/"; }
    }

    @Getter
    private final Image image = new Image(s3);

    @Getter
    @Setter
    @RequiredArgsConstructor
    public static class Image {

        private final S3 s3;

        @Getter
        private final Profile profile = new Profile();

        @Getter
        @Setter
        public class Profile {
            private String prefix;
            private int size;

            public String getBucketBaseUrl() { return s3.getBucketBaseUrl() + prefix; }
        }

        @Getter
        private final Category category = new Category();

        @Getter
        @Setter
        public class Category {
            private String prefix;

            public String getBucketBaseUrl() { return s3.getBucketBaseUrl() + prefix; }
        }
    }
}
