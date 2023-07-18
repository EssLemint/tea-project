package com.lemint.tea.community.response;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@NoArgsConstructor
public class TokenResponse {
  private Long id;
  private String role;
  private String accessToken;

  @Builder
  public TokenResponse(Long id, String role, String accessToken) {
    this.id = id;
    this.role = role;
    this.accessToken = accessToken;
  }
}
