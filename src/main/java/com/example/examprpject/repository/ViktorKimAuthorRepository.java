package com.example.examprpject.repository;

import com.example.examprpject.entity.ViktorKimAuthor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViktorKimAuthorRepository extends JpaRepository<ViktorKimAuthor, Long> {

   Page<ViktorKimAuthor> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
         String firstName, String lastName, Pageable pageable);

   boolean existsByFirstNameAndLastName(String firstName, String lastName);
}