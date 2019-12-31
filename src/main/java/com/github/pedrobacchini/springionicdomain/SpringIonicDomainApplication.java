package com.github.pedrobacchini.springionicdomain;

import com.github.pedrobacchini.springionicdomain.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class SpringIonicDomainApplication implements CommandLineRunner {

    private final S3Service s3Service;

    public static void main(String[] args) { SpringApplication.run(SpringIonicDomainApplication.class, args); }

    @Override
    public void run(String... args) {
        s3Service.uploadFile("C:\\Users\\jwgov\\Downloads\\WhatsApp Image 2019-07-13 at 18.38.11.jpg");
    }
}
