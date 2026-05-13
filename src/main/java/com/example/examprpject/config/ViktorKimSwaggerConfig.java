package com.example.examprpject.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
      name = "bearerAuth",
      type = SecuritySchemeType.HTTP,
      scheme = "bearer",
      bearerFormat = "JWT"
)
public class ViktorKimSwaggerConfig {

   @Bean
   public OpenAPI openAPI() {
      return new OpenAPI()
            .info(new Info()
                  .title("Library Management System API")
                  .description("REST API for managing library books, members and borrowings")
                  .version("1.0.0")
                  .contact(new Contact()
                        .name("Viktor Kim")
                        .email("viktorkim@example.com")
                  )
            );
   }
}