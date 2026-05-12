package com.example.examprpject.mapper;

import com.example.examprpject.dto.response.ViktorKimBorrowingResponse;
import com.example.examprpject.entity.ViktorKimBorrowing;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ViktorKimBookMapper.class, ViktorKimMemberMapper.class})
public interface ViktorKimBorrowingMapper {

   @Mapping(target = "status", expression = "java(borrowing.getStatus().name())")
   ViktorKimBorrowingResponse toResponse(ViktorKimBorrowing borrowing);
}