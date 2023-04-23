package com.lemint.tea.community.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardPostRequest {

  @NotEmpty
  String title;
  @NotEmpty
  String content;
  @NotEmpty
  Long createBy;
  Long modifiedBy;
}
