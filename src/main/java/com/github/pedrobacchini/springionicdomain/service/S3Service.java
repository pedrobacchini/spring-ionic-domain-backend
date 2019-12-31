package com.github.pedrobacchini.springionicdomain.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Log
@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${s3.bucket}")
    private String bucket;

    public void uploadFile(String localFilePath) {
        try {
            File file = new File(localFilePath);
            log.info("Iniciando upload" );
            amazonS3.putObject(new PutObjectRequest(bucket, "teste.jpg", file));
            log.info("Upload finalizado");
        } catch (AmazonServiceException e) {
            log.info("AmazonServiceException: " + e.getMessage());
            log.info("Status Code: " + e.getErrorCode());
        } catch (AmazonClientException e) {
            log.info("AmazonClientException: " + e.getMessage());
        }
    }
}
