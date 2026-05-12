package com.example.examprpject.controller;

import com.example.examprpject.dto.request.ViktorKimBookRequest;
import com.example.examprpject.dto.response.ViktorKimBookResponse;
import com.example.examprpject.service.ViktorKimBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Tag(name = "Books", description = "Book management endpoints")
public class ViktorKimBookController {

   private final ViktorKimBookService bookService;

   @GetMapping
   @Operation(summary = "Get all books with pagination, sorting, search and filter")
   public ResponseEntity<Page<ViktorKimBookResponse>> getAll(
         @RequestParam(required = false) String title,
         @RequestParam(required = false) Long categoryId,
         @RequestParam(required = false) Long authorId,
         @PageableDefault(size = 10, sort = "title") Pageable pageable) {
      return ResponseEntity.ok(bookService.getAll(title, categoryId, authorId, pageable));
   }

   @GetMapping("/{id}")
   @Operation(summary = "Get book by id")
   public ResponseEntity<ViktorKimBookResponse> getById(@PathVariable Long id) {
      return ResponseEntity.ok(bookService.getById(id));
   }

   @PostMapping
   @Operation(summary = "Create book")
   public ResponseEntity<ViktorKimBookResponse> create(@Valid @RequestBody ViktorKimBookRequest request) {
      return ResponseEntity.status(HttpStatus.CREATED).body(bookService.create(request));
   }

   @PutMapping("/{id}")
   @Operation(summary = "Update book")
   public ResponseEntity<ViktorKimBookResponse> update(@PathVariable Long id,
                                                       @Valid @RequestBody ViktorKimBookRequest request) {
      return ResponseEntity.ok(bookService.update(id, request));
   }

   @DeleteMapping("/{id}")
   @Operation(summary = "Delete book")
   public ResponseEntity<Void> delete(@PathVariable Long id) {
      bookService.delete(id);
      return ResponseEntity.noContent().build();
   }
}