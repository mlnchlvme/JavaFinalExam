package com.example.examprpject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
// ... остальные аннотации контроллера

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViktorKimAuthorResponse {

   private Long id;
   private String firstName;
   private String lastName;
   private String biography;
}