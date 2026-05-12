package com.example.examprpject.service;

import com.example.examprpject.exception.ViktorKimFileStorageException;
import com.example.examprpject.exception.ViktorKimResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class ViktorKimFileStorageService {

   private final Path uploadDir;

   public ViktorKimFileStorageService(@Value("${file.upload-dir}") String uploadDir) {
      this.uploadDir = Paths.get(uploadDir).toAbsolutePath().normalize();
      try {
         Files.createDirectories(this.uploadDir);
         log.info("Upload directory created at: {}", this.uploadDir);
      } catch (IOException e) {
         throw new ViktorKimFileStorageException("Could not create upload directory: " + e.getMessage());
      }
   }

   public String storeFile(MultipartFile file) {
      if (file.isEmpty()) {
         throw new ViktorKimFileStorageException("Cannot store empty file");
      }

      String originalFilename = file.getOriginalFilename();
      String extension = "";
      if (originalFilename != null && originalFilename.contains(".")) {
         extension = originalFilename.substring(originalFilename.lastIndexOf("."));
      }

      String filename = UUID.randomUUID() + extension;

      try {
         Path targetPath = uploadDir.resolve(filename);
         Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
         log.info("File stored successfully: {}", filename);
         return filename;
      } catch (IOException e) {
         throw new ViktorKimFileStorageException("Failed to store file: " + e.getMessage());
      }
   }

   public Resource loadFile(String filename) {
      try {
         Path filePath = uploadDir.resolve(filename).normalize();
         Resource resource = new UrlResource(filePath.toUri());
         if (resource.exists() && resource.isReadable()) {
            log.info("File loaded successfully: {}", filename);
            return resource;
         } else {
            throw new ViktorKimResourceNotFoundException("File not found: " + filename);
         }
      } catch (MalformedURLException e) {
         throw new ViktorKimFileStorageException("File path is invalid: " + e.getMessage());
      }
   }

   @Async
   public CompletableFuture<Void> deleteFileAsync(String filename) {
      log.info("Deleting file asynchronously: {}", filename);
      try {
         Path filePath = uploadDir.resolve(filename).normalize();
         Files.deleteIfExists(filePath);
         log.info("File deleted successfully: {}", filename);
      } catch (IOException e) {
         log.error("Failed to delete file: {}", e.getMessage());
      }
      return CompletableFuture.completedFuture(null);
   }
}