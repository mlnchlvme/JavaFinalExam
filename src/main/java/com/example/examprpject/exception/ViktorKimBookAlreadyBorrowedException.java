package com.example.examprpject.exception;

public class ViktorKimBookAlreadyBorrowedException extends RuntimeException {
   public ViktorKimBookAlreadyBorrowedException(String message) {
      super(message);
   }
}