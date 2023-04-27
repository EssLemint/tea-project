package com.lemint.tea.community.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardAttachMappingCreateRequest {
  /**
   * BoardAttachMapping Create
   * 이미지, Board Mapping
   * */

  @Size(min = 1)
  private Integer sort;

  @NotNull
  private Long boardId;

  @NotNull
  private Long attachId;

}
