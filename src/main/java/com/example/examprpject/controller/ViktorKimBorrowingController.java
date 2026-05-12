package com.example.examprpject.controller;

import com.example.examprpject.dto.request.ViktorKimBorrowingRequest;
import com.example.examprpject.dto.response.ViktorKimBorrowingResponse;
import com.example.examprpject.service.ViktorKimBorrowingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrowings")
@RequiredArgsConstructor
@Tag(name = "Borrowings", description = "Borrowing management endpoints")
public class ViktorKimBorrowingController {

   private final ViktorKimBorrowingService borrowingService;

   @GetMapping
   @Operation(summary = "Get all borrowings")
   public ResponseEntity<List<ViktorKimBorrowingResponse>> getAll() {
      return ResponseEntity.ok(borrowingService.getAll());
   }

   @GetMapping("/{id}")
   @Operation(summary = "Get borrowing by id")
   public ResponseEntity<ViktorKimBorrowingResponse> getById(@PathVariable Long id) {
      return ResponseEntity.ok(borrowingService.getById(id));
   }

   @GetMapping("/member/{memberId}")
   @Operation(summary = "Get borrowings by member id")
   public ResponseEntity<List<ViktorKimBorrowingResponse>> getByMemberId(@PathVariable Long memberId) {
      return ResponseEntity.ok(borrowingService.getByMemberId(memberId));
   }

   @PostMapping
   @Operation(summary = "Borrow a book")
   public ResponseEntity<ViktorKimBorrowingResponse> borrowBook(@Valid @RequestBody ViktorKimBorrowingRequest request) {
      return ResponseEntity.status(HttpStatus.CREATED).body(borrowingService.borrowBook(request));
   }

   @PutMapping("/{id}/return")
   @Operation(summary = "Return a book")
   public ResponseEntity<ViktorKimBorrowingResponse> returnBook(@PathVariable Long id) {
      return ResponseEntity.ok(borrowingService.returnBook(id));
   }
}