package com.example.examprpject.repository;

import com.example.examprpject.entity.ViktorKimBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ViktorKimBookRepository extends JpaRepository<ViktorKimBook, Long>,
      JpaSpecificationExecutor<ViktorKimBook> {

   Optional<ViktorKimBook> findByIsbn(String isbn);

   boolean existsByIsbn(String isbn);

   Page<ViktorKimBook> findByTitleContainingIgnoreCase(String title, Pageable pageable);

   Page<ViktorKimBook> findByAuthorId(Long authorId, Pageable pageable);

   Page<ViktorKimBook> findByCategoryId(Long categoryId, Pageable pageable);

   @Query("SELECT b FROM ViktorKimBook b WHERE b.availableCopies > 0")
   Page<ViktorKimBook> findAllAvailable(Pageable pageable);

   @Query("SELECT b FROM ViktorKimBook b WHERE " +
         "LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
         "LOWER(b.author.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
         "LOWER(b.author.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
   Page<ViktorKimBook> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}