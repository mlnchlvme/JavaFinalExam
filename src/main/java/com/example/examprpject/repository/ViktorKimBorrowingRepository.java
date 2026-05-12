package com.example.examprpject.repository;

import com.example.examprpject.entity.ViktorKimBorrowing;
import com.example.examprpject.entity.ViktorKimBorrowing.BorrowingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ViktorKimBorrowingRepository extends JpaRepository<ViktorKimBorrowing, Long> {

   List<ViktorKimBorrowing> findByMemberId(Long memberId);

   Page<ViktorKimBorrowing> findByMemberId(Long memberId, Pageable pageable);

   Page<ViktorKimBorrowing> findByBookId(Long bookId, Pageable pageable);

   Page<ViktorKimBorrowing> findByStatus(BorrowingStatus status, Pageable pageable);

   Optional<ViktorKimBorrowing> findByBookIdAndMemberIdAndStatus(
         Long bookId, Long memberId, BorrowingStatus status);

   boolean existsByBookIdAndMemberIdAndStatus(
         Long bookId, Long memberId, BorrowingStatus status);

   @Query("SELECT b FROM ViktorKimBorrowing b WHERE b.status = 'ACTIVE' AND b.dueDate < :today")
   List<ViktorKimBorrowing> findOverdue(@Param("today") LocalDate today);

   @Query("SELECT b FROM ViktorKimBorrowing b WHERE b.status = 'ACTIVE' AND b.dueDate = :date")
   List<ViktorKimBorrowing> findDueOn(@Param("date") LocalDate date);

   long countByStatus(BorrowingStatus status);

   long countByMemberIdAndStatus(Long memberId, BorrowingStatus status);
}