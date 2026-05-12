package com.example.examprpject.service;

import com.example.examprpject.dto.request.ViktorKimAuthorRequest;
import com.example.examprpject.dto.response.ViktorKimAuthorResponse;
import com.example.examprpject.entity.ViktorKimAuthor;
import com.example.examprpject.exception.ViktorKimResourceNotFoundException;
import com.example.examprpject.mapper.ViktorKimAuthorMapper;
import com.example.examprpject.repository.ViktorKimAuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ViktorKimAuthorService {

   private final ViktorKimAuthorRepository authorRepository;
   private final ViktorKimAuthorMapper authorMapper;

   public List<ViktorKimAuthorResponse> getAll() {
      log.info("Fetching all authors");
      return authorRepository.findAll()
            .stream()
            .map(authorMapper::toResponse)
            .toList();
   }

   public ViktorKimAuthorResponse getById(Long id) {
      log.info("Fetching author by id: {}", id);
      ViktorKimAuthor author = authorRepository.findById(id)
            .orElseThrow(() -> new ViktorKimResourceNotFoundException("Author not found with id: " + id));
      return authorMapper.toResponse(author);
   }

   public ViktorKimAuthorResponse create(ViktorKimAuthorRequest request) {
      log.info("Creating author: {} {}", request.getFirstName(), request.getLastName());
      ViktorKimAuthor author = authorMapper.toEntity(request);
      return authorMapper.toResponse(authorRepository.save(author));
   }

   public ViktorKimAuthorResponse update(Long id, ViktorKimAuthorRequest request) {
      log.info("Updating author with id: {}", id);
      ViktorKimAuthor author = authorRepository.findById(id)
            .orElseThrow(() -> new ViktorKimResourceNotFoundException("Author not found with id: " + id));
      authorMapper.updateEntity(request, author);
      return authorMapper.toResponse(authorRepository.save(author));
   }

   public void delete(Long id) {
      log.info("Deleting author with id: {}", id);
      if (!authorRepository.existsById(id)) {
         throw new ViktorKimResourceNotFoundException("Author not found with id: " + id);
      }
      authorRepository.deleteById(id);
   }
}