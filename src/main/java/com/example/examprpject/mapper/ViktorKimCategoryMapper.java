package com.example.examprpject.mapper;

import com.example.examprpject.dto.request.ViktorKimCategoryRequest;
import com.example.examprpject.dto.response.ViktorKimCategoryResponse;
import com.example.examprpject.entity.ViktorKimCategory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ViktorKimCategoryMapper {

   ViktorKimCategoryResponse toResponse(ViktorKimCategory category);

   ViktorKimCategory toEntity(ViktorKimCategoryRequest request);

   void updateEntity(ViktorKimCategoryRequest request, @MappingTarget ViktorKimCategory category);
}