package com.example.examprpject.controller;

import com.example.examprpject.dto.request.ViktorKimAuthorRequest;
import com.example.examprpject.dto.response.ViktorKimAuthorResponse;
import com.example.examprpject.service.ViktorKimAuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
@Tag(name = "Authors", description = "Author management endpoints")
public class ViktorKimAuthorController {

   private final ViktorKimAuthorService authorService;

   @GetMapping
   @Operation(summary = "Get all authors")
   public ResponseEntity<List<ViktorKimAuthorResponse>> getAll() {
      return ResponseEntity.ok(authorService.getAll());
   }

   @GetMapping("/{id}")
   @Operation(summary = "Get author by id")
   public ResponseEntity<ViktorKimAuthorResponse> getById(@PathVariable Long id) {
      return ResponseEntity.ok(authorService.getById(id));
   }

   @PostMapping
   @Operation(summary = "Create author")
   public ResponseEntity<ViktorKimAuthorResponse> create(@Valid @RequestBody ViktorKimAuthorRequest request) {
      return ResponseEntity.status(HttpStatus.CREATED).body(authorService.create(request));
   }

   @PutMapping("/{id}")
   @Operation(summary = "Update author")
   public ResponseEntity<ViktorKimAuthorResponse> update(@PathVariable Long id,
                                                         @Valid @RequestBody ViktorKimAuthorRequest request) {
      return ResponseEntity.ok(authorService.update(id, request));
   }

   @DeleteMapping("/{id}")
   @Operation(summary = "Delete author")
   public ResponseEntity<Void> delete(@PathVariable Long id) {
      authorService.delete(id);
      return ResponseEntity.noContent().build();
   }
}