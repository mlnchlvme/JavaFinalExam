package com.example.examprpject.service;

import com.example.examprpject.entity.ViktorKimBorrowing;
import com.example.examprpject.repository.ViktorKimBookRepository;
import com.example.examprpject.repository.ViktorKimBorrowingRepository;
import com.example.examprpject.repository.ViktorKimMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class ViktorKimReportService {

   private final ViktorKimBookRepository bookRepository;
   private final ViktorKimBorrowingRepository borrowingRepository;
   private final ViktorKimMemberRepository memberRepository;

   @Async
   public CompletableFuture<Map<String, Long>> generateLibraryStatisticsReport() {
      log.info("Generating library statistics report asynchronously...");
      try {
         Thread.sleep(2000);

         Map<String, Long> stats = new HashMap<>();
         stats.put("totalBooks", bookRepository.count());
         stats.put("totalMembers", memberRepository.count());
         stats.put("totalBorrowings", borrowingRepository.count());
         stats.put("activeBorrowings", borrowingRepository.countByStatus(
               ViktorKimBorrowing.BorrowingStatus.ACTIVE));

         log.info("Library statistics report generated successfully");
         return CompletableFuture.completedFuture(stats);
      } catch (InterruptedException e) {
         log.error("Report generation interrupted: {}", e.getMessage());
         Thread.currentThread().interrupt();
         return CompletableFuture.completedFuture(new HashMap<>());
      }
   }

   @Async
   @Transactional
   public CompletableFuture<Void> processOverdueBooks() {
      log.info("Processing overdue books asynchronously...");
      try {
         Thread.sleep(1500);

         List<ViktorKimBorrowing> overdueList = borrowingRepository.findOverdue(LocalDate.now());

         overdueList.forEach(borrowing -> {
            borrowing.setStatus(ViktorKimBorrowing.BorrowingStatus.OVERDUE);
            borrowingRepository.save(borrowing);
            log.info("Marked borrowing id: {} as OVERDUE", borrowing.getId());
         });

         log.info("Overdue books processing completed. Total: {}", overdueList.size());
      } catch (InterruptedException e) {
         log.error("Overdue processing interrupted: {}", e.getMessage());
         Thread.currentThread().interrupt();
      }
      return CompletableFuture.completedFuture(null);
   }
}