package com.example.examprpject.repository;

import com.example.examprpject.entity.ViktorKimUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ViktorKimUserRepository extends JpaRepository<ViktorKimUser, Long> {

   Optional<ViktorKimUser> findByUsername(String username);

   Optional<ViktorKimUser> findByEmail(String email);

   boolean existsByUsername(String username);

   boolean existsByEmail(String email);
}