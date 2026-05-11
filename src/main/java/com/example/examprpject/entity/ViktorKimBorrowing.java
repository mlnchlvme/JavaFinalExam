package com.example.examprpject.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "borrowings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViktorKimBorrowing {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne
   @JoinColumn(name = "book_id", nullable = false)
   private ViktorKimBook book;

   @ManyToOne
   @JoinColumn(name = "member_id", nullable = false)
   private ViktorKimMember member;

   @Column(nullable = false)
   private LocalDate borrowDate;

   @Column(nullable = false)
   private LocalDate dueDate;

   private LocalDate returnDate;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
   private BorrowingStatus status;

   public enum BorrowingStatus {
      ACTIVE, RETURNED, OVERDUE
   }
}