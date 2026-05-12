package com.example.examprpject.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ViktorKimBorrowingRequest {

   @NotNull(message = "Book ID is required")
   private Long bookId;

   @NotNull(message = "Member ID is required")
   private Long memberId;

   @NotNull(message = "Due date is required")
   @Future(message = "Due date must be in the future")
   private LocalDate dueDate;
}