package com.example.examprpject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import java.util.List;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViktorKimPageResponse<T> {

   private List<T> content;
   private int pageNumber;
   private int pageSize;
   private long totalElements;
   private int totalPages;
   private boolean last;

   public static <T> ViktorKimPageResponse<T> of(Page<T> page) {
      return ViktorKimPageResponse.<T>builder()
            .content(page.getContent())
            .pageNumber(page.getNumber())
            .pageSize(page.getSize())
            .totalElements(page.getTotalElements())
            .totalPages(page.getTotalPages())
            .last(page.isLast())
            .build();
   }
}