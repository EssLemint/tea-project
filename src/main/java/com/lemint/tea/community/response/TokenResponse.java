package com.lemint.tea.community.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Builder
@AllArgsConstructor
public class TokenResponse {
  private Long id;
  private String role;
  private String accessToken;
}
