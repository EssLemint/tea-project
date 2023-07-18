package com.lemint.tea.community.response;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@NoArgsConstructor
@ToString
public class MemberGetResponse {
  private Long id;
  private String name;

  @Builder
  public MemberGetResponse(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
