package com.example.examprpject.mapper;

import com.example.examprpject.dto.request.ViktorKimAuthorRequest;
import com.example.examprpject.dto.response.ViktorKimAuthorResponse;
import com.example.examprpject.entity.ViktorKimAuthor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ViktorKimAuthorMapper {

   ViktorKimAuthorResponse toResponse(ViktorKimAuthor author);

   ViktorKimAuthor toEntity(ViktorKimAuthorRequest request);

   void updateEntity(ViktorKimAuthorRequest request, @MappingTarget ViktorKimAuthor author);
}