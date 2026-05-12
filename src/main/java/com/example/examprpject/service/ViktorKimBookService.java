package com.example.examprpject.service;

import com.example.examprpject.dto.request.ViktorKimBookRequest;
import com.example.examprpject.dto.response.ViktorKimBookResponse;
import com.example.examprpject.entity.ViktorKimAuthor;
import com.example.examprpject.entity.ViktorKimBook;
import com.example.examprpject.entity.ViktorKimCategory;
import com.example.examprpject.exception.ViktorKimResourceNotFoundException;
import com.example.examprpject.mapper.ViktorKimBookMapper;
import com.example.examprpject.repository.ViktorKimAuthorRepository;
import com.example.examprpject.repository.ViktorKimBookRepository;
import com.example.examprpject.repository.ViktorKimCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ViktorKimBookService {

   private final ViktorKimBookRepository bookRepository;
   private final ViktorKimAuthorRepository authorRepository;
   private final ViktorKimCategoryRepository categoryRepository;
   private final ViktorKimBookMapper bookMapper;

   public Page<ViktorKimBookResponse> getAll(String title, Long categoryId, Long authorId, Pageable pageable) {
      log.info("Fetching books with filters - title: {}, categoryId: {}, authorId: {}", title, categoryId, authorId);
      if (title != null) {
         return bookRepository.findByTitleContainingIgnoreCase(title, pageable)
               .map(bookMapper::toResponse);
      }
      if (categoryId != null) {
         return bookRepository.findByCategoryId(categoryId, pageable)
               .map(bookMapper::toResponse);
      }
      if (authorId != null) {
         return bookRepository.findByAuthorId(authorId, pageable)
               .map(bookMapper::toResponse);
      }
      return bookRepository.findAll(pageable).map(bookMapper::toResponse);
   }

   public ViktorKimBookResponse getById(Long id) {
      log.info("Fetching book by id: {}", id);
      ViktorKimBook book = bookRepository.findById(id)
            .orElseThrow(() -> new ViktorKimResourceNotFoundException("Book not found with id: " + id));
      return bookMapper.toResponse(book);
   }

   public ViktorKimBookResponse create(ViktorKimBookRequest request) {
      log.info("Creating book: {}", request.getTitle());
      ViktorKimAuthor author = authorRepository.findById(request.getAuthorId())
            .orElseThrow(() -> new ViktorKimResourceNotFoundException("Author not found with id: " + request.getAuthorId()));
      ViktorKimCategory category = categoryRepository.findById(request.getCategoryId())
            .orElseThrow(() -> new ViktorKimResourceNotFoundException("Category not found with id: " + request.getCategoryId()));
      ViktorKimBook book = bookMapper.toEntity(request);
      book.setAuthor(author);
      book.setCategory(category);
      book.setAvailableCopies(request.getTotalCopies());
      return bookMapper.toResponse(bookRepository.save(book));
   }

   public ViktorKimBookResponse update(Long id, ViktorKimBookRequest request) {
      log.info("Updating book with id: {}", id);
      ViktorKimBook book = bookRepository.findById(id)
            .orElseThrow(() -> new ViktorKimResourceNotFoundException("Book not found with id: " + id));
      ViktorKimAuthor author = authorRepository.findById(request.getAuthorId())
            .orElseThrow(() -> new ViktorKimResourceNotFoundException("Author not found with id: " + request.getAuthorId()));
      ViktorKimCategory category = categoryRepository.findById(request.getCategoryId())
            .orElseThrow(() -> new ViktorKimResourceNotFoundException("Category not found with id: " + request.getCategoryId()));
      bookMapper.updateEntity(request, book);
      book.setAuthor(author);
      book.setCategory(category);
      return bookMapper.toResponse(bookRepository.save(book));
   }

   public void delete(Long id) {
      log.info("Deleting book with id: {}", id);
      if (!bookRepository.existsById(id)) {
         throw new ViktorKimResourceNotFoundException("Book not found with id: " + id);
      }
      bookRepository.deleteById(id);
   }
}