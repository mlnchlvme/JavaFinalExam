package com.example.examprpject.service;

import com.example.examprpject.dto.request.ViktorKimMemberRequest;
import com.example.examprpject.dto.response.ViktorKimMemberResponse;
import com.example.examprpject.entity.ViktorKimMember;
import com.example.examprpject.exception.ViktorKimResourceNotFoundException;
import com.example.examprpject.mapper.ViktorKimMemberMapper;
import com.example.examprpject.repository.ViktorKimMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ViktorKimMemberService {

   private final ViktorKimMemberRepository memberRepository;
   private final ViktorKimMemberMapper memberMapper;

   public List<ViktorKimMemberResponse> getAll() {
      log.info("Fetching all members");
      return memberRepository.findAll()
            .stream()
            .map(memberMapper::toResponse)
            .toList();
   }

   public ViktorKimMemberResponse getById(Long id) {
      log.info("Fetching member by id: {}", id);
      ViktorKimMember member = memberRepository.findById(id)
            .orElseThrow(() -> new ViktorKimResourceNotFoundException("Member not found with id: " + id));
      return memberMapper.toResponse(member);
   }

   public ViktorKimMemberResponse create(ViktorKimMemberRequest request) {
      log.info("Creating member: {} {}", request.getFirstName(), request.getLastName());
      ViktorKimMember member = memberMapper.toEntity(request);
      return memberMapper.toResponse(memberRepository.save(member));
   }

   public ViktorKimMemberResponse update(Long id, ViktorKimMemberRequest request) {
      log.info("Updating member with id: {}", id);
      ViktorKimMember member = memberRepository.findById(id)
            .orElseThrow(() -> new ViktorKimResourceNotFoundException("Member not found with id: " + id));
      memberMapper.updateEntity(request, member);
      return memberMapper.toResponse(memberRepository.save(member));
   }

   public void delete(Long id) {
      log.info("Deleting member with id: {}", id);
      if (!memberRepository.existsById(id)) {
         throw new ViktorKimResourceNotFoundException("Member not found with id: " + id);
      }
      memberRepository.deleteById(id);
   }
}