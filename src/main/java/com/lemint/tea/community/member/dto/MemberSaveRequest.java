package com.lemint.tea.community.member.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberSaveRequest {

  @NotEmpty
  private String userId;

  @Length(min = 8, max = 30)
  private String password;

  @NotEmpty
  @Length(max = 50)
  private String name;
}
