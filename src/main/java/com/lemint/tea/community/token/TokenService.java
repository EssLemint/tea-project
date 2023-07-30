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

  /**
   * @apiNote find token by member seq
   * @return token entity
   * @author lemint
   * @since 2023-07-30
   * */
  public Token findTokenByMemberSeq(Long memberSeq) {
    return repository.findTokenByMemberId(memberSeq);
  }


  /**
   * @apiNote save token entity
   * @param token, member id
   * @return token seq
   * @author lemint
   * @since 2023-07-30
   * */
  @Transactional
  public Long saveToken(String token, Long id) {
    Token entity = Token.createToken(id, token, null);
    //refreshToken 적용 예정
    Long tokenId = repository.save(entity).getId();
    return tokenId;
  }

  /**
   * @apiNote check token entity
   * @param jwt, member id
   * @return true : same token, false : different token
   * @author lemint
   * @since 2023-07-30
   * */
  public Boolean checkToken(Long id, String jwt) {
    Token token = repository.findTokenByMemberId(id);
    return token.getAccessToken().equals(jwt);
  }
}
