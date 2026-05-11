package com.example.examprpject.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViktorKimCategory {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false, unique = true)
   private String name;

   private String description;

   @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
   private List<ViktorKimBook> books;
}