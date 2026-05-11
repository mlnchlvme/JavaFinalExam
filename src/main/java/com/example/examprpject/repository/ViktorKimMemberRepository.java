package com.example.examprpject.repository;

import com.example.examprpject.entity.ViktorKimMember;
import com.example.examprpject.entity.ViktorKimMember.MemberStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ViktorKimMemberRepository extends JpaRepository<ViktorKimMember, Long>,
      JpaSpecificationExecutor<ViktorKimMember> {

   Optional<ViktorKimMember> findByEmail(String email);

   Optional<ViktorKimMember> findByPhoneNumber(String phoneNumber);

   boolean existsByEmail(String email);

   boolean existsByPhoneNumber(String phoneNumber);

   Page<ViktorKimMember> findByStatus(MemberStatus status, Pageable pageable);

   Page<ViktorKimMember> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
         String firstName, String lastName, Pageable pageable);
}