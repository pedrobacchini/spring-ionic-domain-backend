package com.github.pedrobacchini.springionicdomain.service;

import com.github.pedrobacchini.springionicdomain.config.LocaleMessageSource;
import com.github.pedrobacchini.springionicdomain.service.exception.FileException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final LocaleMessageSource localeMessageSource;

    public BufferedImage getJpgImageFromFile(MultipartFile multipartFile) {
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        if (!"png".equals(extension) && !"jpg".equals(extension)) {
            throw new FileException(localeMessageSource.getMessage("only-png-jpg"));
        }
        try {
            BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
            if ("png".equals(extension)) {
                bufferedImage = pngToJpg(bufferedImage);
            }
            return bufferedImage;
        } catch (IOException e) {
            throw new FileException(localeMessageSource.getMessage("error-read-file"));
        }

    }

    private BufferedImage pngToJpg(BufferedImage bufferedImagePng) {
        BufferedImage bufferedImageJpg = new BufferedImage(bufferedImagePng.getWidth(), bufferedImagePng.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        bufferedImageJpg.createGraphics().drawImage(bufferedImagePng, 0, 0, Color.WHITE, null);
        return bufferedImageJpg;
    }

    public InputStream getInputStream(BufferedImage bufferedImage, String extension) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, extension, byteArrayOutputStream);
            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new FileException(localeMessageSource.getMessage("error-write-file"));
        }
    }

    public BufferedImage cropSquare(BufferedImage bufferedImage) {
        int min = Math.min(bufferedImage.getHeight(), bufferedImage.getWidth());
        return Scalr.crop(bufferedImage, (bufferedImage.getWidth()/2) - (min/2),
                (bufferedImage.getHeight()/2) - (min/2), min, min);
    }

    public BufferedImage resize(BufferedImage bufferedImage, int size) {
        return Scalr.resize(bufferedImage, Scalr.Method.ULTRA_QUALITY, size);
    }
}
