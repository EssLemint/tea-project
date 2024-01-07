package com.lemint.tea.community.token;

import com.lemint.tea.entity.Token;

public interface TokenRepositoryCustom {
  Token findTokenByMemberId(Long id);

  Token findTokenByJwt(String jwt);
}
