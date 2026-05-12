package com.example.examprpject.mapper;

import com.example.examprpject.dto.request.ViktorKimBookRequest;
import com.example.examprpject.dto.response.ViktorKimBookResponse;
import com.example.examprpject.entity.ViktorKimBook;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {ViktorKimAuthorMapper.class, ViktorKimCategoryMapper.class})
public interface ViktorKimBookMapper {

   ViktorKimBookResponse toResponse(ViktorKimBook book);

   @Mapping(target = "id", ignore = true)
   @Mapping(target = "author", ignore = true)
   @Mapping(target = "category", ignore = true)
   @Mapping(target = "availableCopies", ignore = true)
   ViktorKimBook toEntity(ViktorKimBookRequest request);

   @Mapping(target = "id", ignore = true)
   @Mapping(target = "author", ignore = true)
   @Mapping(target = "category", ignore = true)
   @Mapping(target = "availableCopies", ignore = true)
   void updateEntity(ViktorKimBookRequest request, @MappingTarget ViktorKimBook book);
}