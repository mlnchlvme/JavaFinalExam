package com.example.examprpject.service;

import com.example.examprpject.dto.request.ViktorKimLoginRequest;
import com.example.examprpject.dto.request.ViktorKimRegisterRequest;
import com.example.examprpject.dto.response.ViktorKimAuthResponse;
import com.example.examprpject.entity.ViktorKimUser;
import com.example.examprpject.exception.ViktorKimResourceNotFoundException;
import com.example.examprpject.repository.ViktorKimUserRepository;
import com.example.examprpject.security.ViktorKimJwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ViktorKimAuthService {

   private final ViktorKimUserRepository userRepository;
   private final ViktorKimJwtUtil jwtUtil;
   private final PasswordEncoder passwordEncoder;

   public ViktorKimAuthResponse register(ViktorKimRegisterRequest request) {
      log.info("Registering new user: {}", request.getUsername());

      if (userRepository.existsByUsername(request.getUsername())) {
         throw new BadCredentialsException("Username already exists: " + request.getUsername());
      }
      if (userRepository.existsByEmail(request.getEmail())) {
         throw new BadCredentialsException("Email already exists: " + request.getEmail());
      }

      ViktorKimUser user = ViktorKimUser.builder()
            .username(request.getUsername())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(ViktorKimUser.Role.ROLE_USER)
            .build();

      userRepository.save(user);

      String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
      log.info("User registered successfully: {}", user.getUsername());

      return ViktorKimAuthResponse.builder()
            .token(token)
            .username(user.getUsername())
            .email(user.getEmail())
            .role(user.getRole().name())
            .build();
   }

   public ViktorKimAuthResponse login(ViktorKimLoginRequest request) {
      log.info("Logging in user: {}", request.getUsername());

      ViktorKimUser user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new ViktorKimResourceNotFoundException("User not found: " + request.getUsername()));

      if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
         throw new BadCredentialsException("Invalid password for user: " + request.getUsername());
      }

      String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
      log.info("User logged in successfully: {}", user.getUsername());

      return ViktorKimAuthResponse.builder()
            .token(token)
            .username(user.getUsername())
            .email(user.getEmail())
            .role(user.getRole().name())
            .build();
   }
}