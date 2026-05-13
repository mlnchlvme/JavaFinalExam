package com.example.examprpject.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@Slf4j
public class ViktorKimJwtUtil {

   @Value("${jwt.secret}")
   private String secret;

   @Value("${jwt.expiration}")
   private Long expiration;

   private SecretKey getSigningKey() {
      return Keys.hmacShaKeyFor(secret.getBytes());
   }

   public String generateToken(String username, String role) {
      log.info("Generating JWT token for user: {}", username);
      return Jwts.builder()
            .subject(username)
            .claim("role", role)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSigningKey())
            .compact();
   }

   public String extractUsername(String token) {
      return getClaims(token).getSubject();
   }

   public String extractRole(String token) {
      return getClaims(token).get("role", String.class);
   }

   public boolean isTokenValid(String token) {
      try {
         getClaims(token);
         return true;
      } catch (Exception e) {
         log.error("Invalid JWT token: {}", e.getMessage());
         return false;
      }
   }

   private Claims getClaims(String token) {
      return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
   }
}