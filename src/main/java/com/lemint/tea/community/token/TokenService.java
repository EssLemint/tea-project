package com.lemint.tea.community.token;

import com.lemint.tea.entity.Member;
import com.lemint.tea.entity.Token;
import com.lemint.tea.enums.Role;
import com.lemint.tea.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TokenService {

  private final TokenRepository repository;

  public Token findTokenByMemberSeq(Long memberSeq) {
    return repository.findTokenByMemberId(memberSeq);
  }

  @Transactional
  public Long saveToken(String token, Member member) {
    Token entity = Token.createToken(member.getId(), token, null);
    //refreshToken 이후
    Long id = repository.save(entity).getId();
    return id;
  }

  public Boolean checkToken(Long id, String jwt) {
    Token token = repository.findTokenByMemberId(id);
    return token.getAccessToken().equals(jwt);
  }
}
