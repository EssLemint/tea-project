package com.lemint.tea.community.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
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
