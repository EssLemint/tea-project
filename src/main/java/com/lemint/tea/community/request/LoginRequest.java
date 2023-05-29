package com.lemint.tea.community.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {
  @NotEmpty
  private String userId;
  @NotEmpty
  private String password;
}
