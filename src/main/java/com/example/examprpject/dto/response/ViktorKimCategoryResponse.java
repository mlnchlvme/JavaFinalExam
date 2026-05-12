package com.example.examprpject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViktorKimCategoryResponse {

   private Long id;
   private String name;
   private String description;
}