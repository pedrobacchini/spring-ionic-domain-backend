package com.github.pedrobacchini.springionicdomain.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class S3Config {

    private final ApplicationProperties applicationProperties;

    @Bean
    public AmazonS3 amazonS3() {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(
                applicationProperties.getAws().getAccessKeyId(),
                applicationProperties.getAws().getSecretAccessKey()
        );
        return AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(applicationProperties.getS3().getRegion()))
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials)).build();
    }
}
