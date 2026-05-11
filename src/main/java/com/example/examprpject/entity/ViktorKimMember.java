package com.example.examprpject.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViktorKimMember {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false)
   private String firstName;

   @Column(nullable = false)
   private String lastName;

   @Column(nullable = false, unique = true)
   private String email;

   @Column(nullable = false, unique = true)
   private String phoneNumber;

   @Column(nullable = false)
   private LocalDate membershipDate;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
   private MemberStatus status;

   public enum MemberStatus {
      ACTIVE, SUSPENDED, EXPIRED
   }

   @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
   private List<ViktorKimBorrowing> borrowings;
}