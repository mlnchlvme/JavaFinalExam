package com.example.examprpject.mapper;

import com.example.examprpject.dto.request.ViktorKimMemberRequest;
import com.example.examprpject.dto.response.ViktorKimMemberResponse;
import com.example.examprpject.entity.ViktorKimMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ViktorKimMemberMapper {

   @Mapping(target = "status", expression = "java(member.getStatus().name())")
   ViktorKimMemberResponse toResponse(ViktorKimMember member);

   @Mapping(target = "id", ignore = true)
   @Mapping(target = "membershipDate", ignore = true)
   @Mapping(target = "borrowings", ignore = true)
   ViktorKimMember toEntity(ViktorKimMemberRequest request);

   @Mapping(target = "id", ignore = true)
   @Mapping(target = "membershipDate", ignore = true)
   @Mapping(target = "borrowings", ignore = true)
   void updateEntity(ViktorKimMemberRequest request, @MappingTarget ViktorKimMember member);
}