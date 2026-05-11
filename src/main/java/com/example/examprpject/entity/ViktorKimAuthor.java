package com.example.examprpject.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "authors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViktorKimAuthor {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false)
   private String firstName;

   @Column(nullable = false)
   private String lastName;

   private String biography;

   @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
   private List<ViktorKimBook> books;
}