package com.catholic.moyeo.common.service;

import com.catholic.moyeo.member.domain.Member;
import com.catholic.moyeo.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileUploadService {

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final int IMAGE_SIZE = 1080; // 썸네일 기준

    public String uploadThumbnail(MultipartFile file, String folder) {
        validateFile(file);
        String format = getFormatName(file);

        String fileName = generateFileName(format);

        // 실제 저장 경로
        String savePath = System.getProperty("user.dir")
                + "/uploads/" + folder + "/" + fileName;

        try {
            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            if (originalImage == null)
                throw new BadRequestException("Invalid image file");

            BufferedImage squareImage = cropToSquare(originalImage);
            BufferedImage resizedImage = resizeImage(squareImage, IMAGE_SIZE, IMAGE_SIZE);

            File outputFile = new File(savePath);
            createDirectoryIfNotExists(outputFile.getParentFile());
            ImageIO.write(resizedImage, format, outputFile);

            // 프론트 URL
            return "/uploads/" + folder + "/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload thumbnail", e);
        }
    }

    public String uploadOriginal(MultipartFile file, String folder) {
        validateFile(file);
        String format = getFormatName(file);

        String fileName = generateFileName(format);

        // 실제 저장 경로
        String savePath = System.getProperty("user.dir")
                + "/uploads/" + folder + "/" + fileName;

        try {
            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            if (originalImage == null)
                throw new BadRequestException("Invalid image file");

            File outputFile = new File(savePath);
            createDirectoryIfNotExists(outputFile.getParentFile());
            ImageIO.write(originalImage, format, outputFile);

            // 프론트에 줄 URL
            return "/uploads/" + folder + "/" + fileName;

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload original image", e);
        }
    }

    public void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        // if (file.getSize() > MAX_FILE_SIZE) {
        // throw new IllegalArgumentException("File size exceeds 5MB");
        // }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }
    }

    private String getFormatName(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null)
            return "jpg";

        return switch (contentType) {
            case "image/webp" -> "webp";
            case "image/png" -> "png";
            case "image/gif" -> "gif";
            default -> "jpg";
        };
    }

    private String generateFileName(String format) {
        return UUID.randomUUID() + "." + format;
    }

    private void createDirectoryIfNotExists(File directory) {
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                throw new RuntimeException("Failed to create directory: " + directory.getAbsolutePath());
            }
        }
    }

    private BufferedImage cropToSquare(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int squareSize = Math.min(width, height);
        int x = (width - squareSize) / 2;
        int y = (height - squareSize) / 2;
        return image.getSubimage(x, y, squareSize, squareSize);
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();

        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();

        return resizedImage;
    }

}