package com.example.examprpject.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ViktorKimBookRequest {

   @NotBlank(message = "Title is required")
   private String title;

   @NotBlank(message = "ISBN is required")
   private String isbn;

   private Integer publishedYear;

   @NotNull(message = "Total copies is required")
   @Min(value = 1, message = "Total copies must be at least 1")
   private Integer totalCopies;

   @NotNull(message = "Author ID is required")
   private Long authorId;

   @NotNull(message = "Category ID is required")
   private Long categoryId;
}