package com.example.examprpject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViktorKimBook {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false)
   private String title;

   @Column(nullable = false, unique = true)
   private String isbn;

   private Integer publishedYear;

   @Column(nullable = false)
   private Integer totalCopies;

   @Column(nullable = false)
   private Integer availableCopies;

   @ManyToOne
   @JoinColumn(name = "author_id", nullable = false)
   private ViktorKimAuthor author;

   @ManyToOne
   @JoinColumn(name = "category_id", nullable = false)
   private ViktorKimCategory category;
}