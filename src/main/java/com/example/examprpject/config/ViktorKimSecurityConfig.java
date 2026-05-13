package com.example.examprpject.config;

import com.example.examprpject.security.ViktorKimJwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ViktorKimSecurityConfig {

   private final ViktorKimJwtAuthFilter jwtAuthFilter;

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                  .requestMatchers("/api/auth/**").permitAll()
                  .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                  .requestMatchers(HttpMethod.GET, "/api/books/**").hasAnyRole("USER", "LIBRARIAN", "ADMIN")
                  .requestMatchers(HttpMethod.GET, "/api/authors/**").hasAnyRole("USER", "LIBRARIAN", "ADMIN")
                  .requestMatchers(HttpMethod.GET, "/api/categories/**").hasAnyRole("USER", "LIBRARIAN", "ADMIN")
                  .requestMatchers("/api/books/**").hasAnyRole("LIBRARIAN", "ADMIN")
                  .requestMatchers("/api/authors/**").hasAnyRole("LIBRARIAN", "ADMIN")
                  .requestMatchers("/api/categories/**").hasAnyRole("LIBRARIAN", "ADMIN")
                  .requestMatchers("/api/members/**").hasRole("ADMIN")
                  .requestMatchers("/api/borrowings/**").hasAnyRole("LIBRARIAN", "ADMIN")
                  .requestMatchers("/api/files/**").hasAnyRole("LIBRARIAN", "ADMIN")
                  .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

      return http.build();
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
      return config.getAuthenticationManager();
   }
}