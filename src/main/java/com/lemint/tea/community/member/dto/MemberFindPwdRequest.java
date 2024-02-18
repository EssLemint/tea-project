package com.lemint.tea.community.member.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class MemberFindPwdRequest {
  @NotEmpty
  private String userId;
}
