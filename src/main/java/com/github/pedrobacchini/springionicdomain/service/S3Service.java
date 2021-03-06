package com.github.pedrobacchini.springionicdomain.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.github.pedrobacchini.springionicdomain.config.ApplicationProperties;
import com.github.pedrobacchini.springionicdomain.config.LocaleMessageSource;
import com.github.pedrobacchini.springionicdomain.service.exception.FileException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Log
@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;
    private final LocaleMessageSource localeMessageSource;
    private final ApplicationProperties applicationProperties;

    public URI uploadFile(MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();
            InputStream inputStream = multipartFile.getInputStream();
            String contentFile = multipartFile.getContentType();
            return uploadFile(inputStream, fileName, contentFile);
        } catch (IOException e) {
            throw new FileException(localeMessageSource.getMessage("io-error", e.getMessage()));
        }
    }

    public URI uploadFile(InputStream inputStream, String fileName, String contentType) {
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(contentType);
            objectMetadata.setContentLength(inputStream.available());
            log.info("Iniciando upload");
            amazonS3.putObject(applicationProperties.getS3().getBucket(), fileName, inputStream, objectMetadata);
            log.info("Upload finalizado");
            return amazonS3.getUrl(applicationProperties.getS3().getBucket(), fileName).toURI();
        } catch (URISyntaxException e) {
            throw new FileException(localeMessageSource.getMessage("error-convert-url"));
        } catch (IOException e) {
            throw new FileException(localeMessageSource.getMessage("error-determine-size-file"));
        }
    }
}
