package com.example.examprpject.service;

import com.example.examprpject.dto.request.ViktorKimBorrowingRequest;
import com.example.examprpject.dto.response.ViktorKimBorrowingResponse;
import com.example.examprpject.entity.ViktorKimBook;
import com.example.examprpject.entity.ViktorKimBorrowing;
import com.example.examprpject.entity.ViktorKimMember;
import com.example.examprpject.exception.ViktorKimBookAlreadyBorrowedException;
import com.example.examprpject.exception.ViktorKimResourceNotFoundException;
import com.example.examprpject.mapper.ViktorKimBorrowingMapper;
import com.example.examprpject.repository.ViktorKimBookRepository;
import com.example.examprpject.repository.ViktorKimBorrowingRepository;
import com.example.examprpject.repository.ViktorKimMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ViktorKimBorrowingService {

   private final ViktorKimBorrowingRepository borrowingRepository;
   private final ViktorKimBookRepository bookRepository;
   private final ViktorKimMemberRepository memberRepository;
   private final ViktorKimBorrowingMapper borrowingMapper;
   private final ViktorKimEmailNotificationService emailNotificationService;

   public List<ViktorKimBorrowingResponse> getAll() {
      log.info("Fetching all borrowings");
      return borrowingRepository.findAll()
            .stream()
            .map(borrowingMapper::toResponse)
            .toList();
   }

   public ViktorKimBorrowingResponse getById(Long id) {
      log.info("Fetching borrowing by id: {}", id);
      ViktorKimBorrowing borrowing = borrowingRepository.findById(id)
            .orElseThrow(() -> new ViktorKimResourceNotFoundException("Borrowing not found with id: " + id));
      return borrowingMapper.toResponse(borrowing);
   }

   @Transactional
   public ViktorKimBorrowingResponse borrowBook(ViktorKimBorrowingRequest request) {
      log.info("Borrowing book id: {} for member id: {}", request.getBookId(), request.getMemberId());

      ViktorKimBook book = bookRepository.findById(request.getBookId())
            .orElseThrow(() -> new ViktorKimResourceNotFoundException("Book not found with id: " + request.getBookId()));

      if (book.getAvailableCopies() <= 0) {
         throw new ViktorKimBookAlreadyBorrowedException("No available copies for book: " + book.getTitle());
      }

      ViktorKimMember member = memberRepository.findById(request.getMemberId())
            .orElseThrow(() -> new ViktorKimResourceNotFoundException("Member not found with id: " + request.getMemberId()));

      book.setAvailableCopies(book.getAvailableCopies() - 1);
      bookRepository.save(book);

      ViktorKimBorrowing borrowing = ViktorKimBorrowing.builder()
            .book(book)
            .member(member)
            .borrowDate(LocalDate.now())
            .dueDate(request.getDueDate())
            .status(ViktorKimBorrowing.BorrowingStatus.ACTIVE)
            .build();

      ViktorKimBorrowingResponse response = borrowingMapper.toResponse(borrowingRepository.save(borrowing));

      emailNotificationService.sendBorrowingConfirmation(
            member.getEmail(),
            book.getTitle()
      );

      return response;
   }

   @Transactional
   public ViktorKimBorrowingResponse returnBook(Long id) {
      log.info("Returning borrowing with id: {}", id);

      ViktorKimBorrowing borrowing = borrowingRepository.findById(id)
            .orElseThrow(() -> new ViktorKimResourceNotFoundException("Borrowing not found with id: " + id));

      if (borrowing.getStatus() == ViktorKimBorrowing.BorrowingStatus.RETURNED) {
         throw new ViktorKimBookAlreadyBorrowedException("Borrowing with id " + id + " is already returned");
      }

      ViktorKimBook book = borrowing.getBook();
      book.setAvailableCopies(book.getAvailableCopies() + 1);
      bookRepository.save(book);

      borrowing.setReturnDate(LocalDate.now());
      borrowing.setStatus(ViktorKimBorrowing.BorrowingStatus.RETURNED);

      ViktorKimBorrowingResponse response = borrowingMapper.toResponse(borrowingRepository.save(borrowing));

      emailNotificationService.sendReturnConfirmation(
            borrowing.getMember().getEmail(),
            borrowing.getBook().getTitle()
      );

      return response;
   }

   public List<ViktorKimBorrowingResponse> getByMemberId(Long memberId) {
      log.info("Fetching borrowings for member id: {}", memberId);
      return borrowingRepository.findByMemberId(memberId)
            .stream()
            .map(borrowingMapper::toResponse)
            .toList();
   }
}