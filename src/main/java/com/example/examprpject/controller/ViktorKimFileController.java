package com.example.examprpject.controller;

import com.example.examprpject.service.ViktorKimFileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Tag(name = "Files", description = "File upload and download endpoints")
public class ViktorKimFileController {

   private final ViktorKimFileStorageService fileStorageService;

   @PostMapping("/upload")
   @Operation(summary = "Upload a file")
   public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
      String filename = fileStorageService.storeFile(file);
      return ResponseEntity.ok(Map.of(
            "filename", filename,
            "message", "File uploaded successfully"
      ));
   }

   @GetMapping("/download/{filename}")
   @Operation(summary = "Download a file by filename")
   public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
      Resource resource = fileStorageService.loadFile(filename);
      return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .header(HttpHeaders.CONTENT_DISPOSITION,
                  "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
   }

   @DeleteMapping("/{filename}")
   @Operation(summary = "Delete a file by filename")
   public ResponseEntity<Map<String, String>> deleteFile(@PathVariable String filename) {
      fileStorageService.deleteFileAsync(filename);
      return ResponseEntity.ok(Map.of("message", "File deletion started"));
   }
}