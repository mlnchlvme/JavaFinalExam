package com.example.examprpject.controller;

import com.example.examprpject.dto.request.ViktorKimMemberRequest;
import com.example.examprpject.dto.response.ViktorKimMemberResponse;
import com.example.examprpject.service.ViktorKimMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Tag(name = "Members", description = "Member management endpoints")
public class ViktorKimMemberController {

   private final ViktorKimMemberService memberService;

   @GetMapping
   @Operation(summary = "Get all members")
   public ResponseEntity<List<ViktorKimMemberResponse>> getAll() {
      return ResponseEntity.ok(memberService.getAll());
   }

   @GetMapping("/{id}")
   @Operation(summary = "Get member by id")
   public ResponseEntity<ViktorKimMemberResponse> getById(@PathVariable Long id) {
      return ResponseEntity.ok(memberService.getById(id));
   }

   @PostMapping
   @Operation(summary = "Create member")
   public ResponseEntity<ViktorKimMemberResponse> create(@Valid @RequestBody ViktorKimMemberRequest request) {
      return ResponseEntity.status(HttpStatus.CREATED).body(memberService.create(request));
   }

   @PutMapping("/{id}")
   @Operation(summary = "Update member")
   public ResponseEntity<ViktorKimMemberResponse> update(@PathVariable Long id,
                                                         @Valid @RequestBody ViktorKimMemberRequest request) {
      return ResponseEntity.ok(memberService.update(id, request));
   }

   @DeleteMapping("/{id}")
   @Operation(summary = "Delete member")
   public ResponseEntity<Void> delete(@PathVariable Long id) {
      memberService.delete(id);
      return ResponseEntity.noContent().build();
   }
}