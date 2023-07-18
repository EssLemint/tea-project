package com.lemint.tea.community.response;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@NoArgsConstructor
@ToString
public class BoardResponse {
  private String title;
  private String content;
  private Long createBy;
  private Integer views;

  @Builder
  public BoardResponse(String title, String content, Long createBy, Integer views) {
    this.title = title;
    this.content = content;
    this.createBy = createBy;
    this.views = views;
  }
}
