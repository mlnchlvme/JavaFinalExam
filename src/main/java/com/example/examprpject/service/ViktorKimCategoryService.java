package com.example.examprpject.service;

import com.example.examprpject.dto.request.ViktorKimCategoryRequest;
import com.example.examprpject.dto.response.ViktorKimCategoryResponse;
import com.example.examprpject.entity.ViktorKimCategory;
import com.example.examprpject.exception.ViktorKimResourceNotFoundException;
import com.example.examprpject.mapper.ViktorKimCategoryMapper;
import com.example.examprpject.repository.ViktorKimCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ViktorKimCategoryService {

   private final ViktorKimCategoryRepository categoryRepository;
   private final ViktorKimCategoryMapper categoryMapper;

   public List<ViktorKimCategoryResponse> getAll() {
      log.info("Fetching all categories");
      return categoryRepository.findAll()
            .stream()
            .map(categoryMapper::toResponse)
            .toList();
   }

   public ViktorKimCategoryResponse getById(Long id) {
      log.info("Fetching category by id: {}", id);
      ViktorKimCategory category = categoryRepository.findById(id)
            .orElseThrow(() -> new ViktorKimResourceNotFoundException("Category not found with id: " + id));
      return categoryMapper.toResponse(category);
   }

   public ViktorKimCategoryResponse create(ViktorKimCategoryRequest request) {
      log.info("Creating category: {}", request.getName());
      ViktorKimCategory category = categoryMapper.toEntity(request);
      return categoryMapper.toResponse(categoryRepository.save(category));
   }

   public ViktorKimCategoryResponse update(Long id, ViktorKimCategoryRequest request) {
      log.info("Updating category with id: {}", id);
      ViktorKimCategory category = categoryRepository.findById(id)
            .orElseThrow(() -> new ViktorKimResourceNotFoundException("Category not found with id: " + id));
      categoryMapper.updateEntity(request, category);
      return categoryMapper.toResponse(categoryRepository.save(category));
   }

   public void delete(Long id) {
      log.info("Deleting category with id: {}", id);
      if (!categoryRepository.existsById(id)) {
         throw new ViktorKimResourceNotFoundException("Category not found with id: " + id);
      }
      categoryRepository.deleteById(id);
   }
}