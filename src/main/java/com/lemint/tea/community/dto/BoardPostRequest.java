package com.lemint.tea.community.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardPostRequest {
  /**
   * BoardPostRequest > AttachMappingCreateRequest
   * 이미지 Mapping Create
   * */

  @NotEmpty
  private String title;
  @NotEmpty
  private String content;
  @NotEmpty
  private Long createBy;
  @NotEmpty
  private Long modifiedBy;
  /**
   * Request로 떤질때... sort 순서 정보는 앞에서 떤져주고
   * 안의 board id, attach id는 저장하면서 변경을 해줘야할듯
   * */
  private List<BoardAttachMappingCreateRequest> attachMappingList;
}
