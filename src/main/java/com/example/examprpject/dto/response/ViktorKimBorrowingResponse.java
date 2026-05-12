package com.example.examprpject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViktorKimBorrowingResponse {

   private Long id;
   private ViktorKimBookResponse book;
   private ViktorKimMemberResponse member;
   private LocalDate borrowDate;
   private LocalDate dueDate;
   private LocalDate returnDate;
   private String status;
}