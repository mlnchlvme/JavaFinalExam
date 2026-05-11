package com.example.examprpject.repository;

import com.example.examprpject.entity.ViktorKimCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ViktorKimCategoryRepository extends JpaRepository<ViktorKimCategory, Long> {

   Optional<ViktorKimCategory> findByName(String name);

   boolean existsByName(String name);
}