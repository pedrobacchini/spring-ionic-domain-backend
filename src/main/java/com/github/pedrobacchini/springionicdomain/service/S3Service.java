package com.github.pedrobacchini.springionicdomain.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.github.pedrobacchini.springionicdomain.service.exception.FileException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${s3.bucket}")
    private String bucket;

    public URI uploadFile(MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();
            InputStream inputStream = multipartFile.getInputStream();
            String contentFile = multipartFile.getContentType();
            return uploadFile(inputStream, fileName, contentFile);
        } catch (IOException e) {
            throw new FileException("Erro de IO: " + e.getMessage());
        }
    }

    public URI uploadFile(InputStream inputStream, String fileName, String contentType) {
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(contentType);
            log.info("Iniciando upload");
            amazonS3.putObject(bucket, fileName, inputStream, objectMetadata);
            log.info("Upload finalizado");
            return amazonS3.getUrl(bucket, fileName).toURI();
        } catch (URISyntaxException e) {
            throw new FileException("Erro ao converter URL para URI");
        }
    }
}
