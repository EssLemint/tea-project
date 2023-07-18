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

  @NotNull
  @NotEmpty
  private String userId;

  @NotNull
  @Length(min = 8, max = 30)
  private String password;

  @NotNull
  @Length(max = 50)
  private String name;
}
