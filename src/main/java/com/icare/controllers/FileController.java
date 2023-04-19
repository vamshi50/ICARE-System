package com.icare.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

@RestController
public class FileController {

    private final Path imageStorageDir = Path.of("upload");

    /*
    The target path can be configured in the application.properties / application.yml or using the parameter -Dimage-storage.dir=/some/path/
     */
    @Autowired
//    public FileController(@Value("${image-storage-dir}") Path imageStorageDir) {
//        this.imageStorageDir = imageStorageDir;
//    }

    @PostConstruct
    public void ensureDirectoryExists() throws IOException {
        if (!Files.exists(this.imageStorageDir)) {
            Files.createDirectories(this.imageStorageDir);
        }
    }

    /*
    This enables you to perform POST requests against the "/image/YourID" path
    It returns the name this image can be referenced on later
     */
    public String getFileName(MultipartFile file, String name) {
        final String fileExtension = Optional.ofNullable(file.getOriginalFilename())
                .flatMap(FileController::getFileExtension)
                .orElse("");

        final String targetFileName = name + "." + fileExtension;
        return targetFileName;
    }

    public String uploadImage(MultipartFile imageFile, String name) throws IOException {
        String targetFileName = getFileName(imageFile, name);
        final Path targetPath = this.imageStorageDir.resolve(targetFileName);

        try (InputStream in = imageFile.getInputStream()) {
            try (OutputStream out = Files.newOutputStream(targetPath, StandardOpenOption.CREATE)) {
                in.transferTo(out);
            }
        }

        return targetFileName;
    }

    /*
    This enables you to download previously uploaded images
     */
    @GetMapping("file/{fileName}")
    public ResponseEntity<Resource> downloadImage(@PathVariable("fileName") String fileName) {
        System.out.println("fileName: " + fileName);
        final Path targetPath = imageStorageDir.resolve(fileName);
        if (!Files.exists(targetPath)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new PathResource(targetPath));
    }

    private static Optional<String> getFileExtension(String fileName) {
        final int indexOfLastDot = fileName.lastIndexOf('.');

        if (indexOfLastDot == -1) {
            return Optional.empty();
        } else {
            return Optional.of(fileName.substring(indexOfLastDot + 1));
        }
    }
}
