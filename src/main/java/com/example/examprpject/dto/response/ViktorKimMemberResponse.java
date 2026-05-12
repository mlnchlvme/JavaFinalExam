package com.example.examprpject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
// ... остальные аннотации контроллера

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViktorKimMemberResponse {

   private Long id;
   private String firstName;
   private String lastName;
   private String email;
   private String phoneNumber;
   private LocalDate membershipDate;
   private String status;
}