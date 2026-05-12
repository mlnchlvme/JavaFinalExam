package com.example.examprpject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class ViktorKimEmailNotificationService {

   private final JavaMailSender mailSender;

   @Async
   public CompletableFuture<Void> sendBorrowingConfirmation(String memberEmail, String bookTitle) {
      log.info("Sending borrowing confirmation email to: {} for book: {}", memberEmail, bookTitle);
      try {
         SimpleMailMessage message = new SimpleMailMessage();
         message.setTo(memberEmail);
         message.setSubject("Book Borrowed Successfully");
         message.setText("""
                    Hello!
                    
                    You have successfully borrowed the book: "%s"
                    
                    Please return it within 14 days.
                    
                    Thank you for using our library!
                    """.formatted(bookTitle));

         mailSender.send(message);
         log.info("Borrowing confirmation email sent successfully to: {}", memberEmail);
      } catch (Exception e) {
         log.error("Failed to send borrowing confirmation email to {}: {}", memberEmail, e.getMessage());
      }
      return CompletableFuture.completedFuture(null);
   }

   @Async
   public CompletableFuture<Void> sendReturnConfirmation(String memberEmail, String bookTitle) {
      log.info("Sending return confirmation email to: {} for book: {}", memberEmail, bookTitle);
      try {
         SimpleMailMessage message = new SimpleMailMessage();
         message.setTo(memberEmail);
         message.setSubject("Book Returned Successfully");
         message.setText("""
                    Hello!
                    
                    You have successfully returned the book: "%s"
                    
                    Thank you for using our library!
                    """.formatted(bookTitle));

         mailSender.send(message);
         log.info("Return confirmation email sent successfully to: {}", memberEmail);
      } catch (Exception e) {
         log.error("Failed to send return confirmation email to {}: {}", memberEmail, e.getMessage());
      }
      return CompletableFuture.completedFuture(null);
   }

   @Async
   public CompletableFuture<Void> sendOverdueNotification(String memberEmail, String bookTitle) {
      log.info("Sending overdue notification email to: {} for book: {}", memberEmail, bookTitle);
      try {
         SimpleMailMessage message = new SimpleMailMessage();
         message.setTo(memberEmail);
         message.setSubject("Overdue Book Notice");
         message.setText("""
                    Hello!
                    
                    The book "%s" that you borrowed is OVERDUE.
                    
                    Please return it as soon as possible to avoid penalties.
                    
                    Thank you!
                    """.formatted(bookTitle));

         mailSender.send(message);
         log.info("Overdue notification email sent successfully to: {}", memberEmail);
      } catch (Exception e) {
         log.error("Failed to send overdue notification email to {}: {}", memberEmail, e.getMessage());
      }
      return CompletableFuture.completedFuture(null);
   }
}