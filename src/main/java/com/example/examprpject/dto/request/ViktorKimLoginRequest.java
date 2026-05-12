package com.example.examprpject.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ViktorKimLoginRequest {

   @NotBlank(message = "Username is required")
   private String username;

   @NotBlank(message = "Password is required")
   private String password;
}