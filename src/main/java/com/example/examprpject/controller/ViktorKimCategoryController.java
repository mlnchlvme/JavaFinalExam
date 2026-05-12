package com.example.examprpject.controller;

import com.example.examprpject.dto.request.ViktorKimCategoryRequest;
import com.example.examprpject.dto.response.ViktorKimCategoryResponse;
import com.example.examprpject.service.ViktorKimCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Categories", description = "Category management endpoints")
public class ViktorKimCategoryController {

   private final ViktorKimCategoryService categoryService;

   @GetMapping
   @Operation(summary = "Get all categories")
   public ResponseEntity<List<ViktorKimCategoryResponse>> getAll() {
      return ResponseEntity.ok(categoryService.getAll());
   }

   @GetMapping("/{id}")
   @Operation(summary = "Get category by id")
   public ResponseEntity<ViktorKimCategoryResponse> getById(@PathVariable Long id) {
      return ResponseEntity.ok(categoryService.getById(id));
   }

   @PostMapping
   @Operation(summary = "Create category")
   public ResponseEntity<ViktorKimCategoryResponse> create(@Valid @RequestBody ViktorKimCategoryRequest request) {
      return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(request));
   }

   @PutMapping("/{id}")
   @Operation(summary = "Update category")
   public ResponseEntity<ViktorKimCategoryResponse> update(@PathVariable Long id,
                                                           @Valid @RequestBody ViktorKimCategoryRequest request) {
      return ResponseEntity.ok(categoryService.update(id, request));
   }

   @DeleteMapping("/{id}")
   @Operation(summary = "Delete category")
   public ResponseEntity<Void> delete(@PathVariable Long id) {
      categoryService.delete(id);
      return ResponseEntity.noContent().build();
   }
}