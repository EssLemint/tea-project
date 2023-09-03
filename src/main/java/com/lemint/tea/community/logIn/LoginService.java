package com.lemint.tea.community.logIn;

import com.lemint.tea.community.exception.CustomException;
import com.lemint.tea.community.exception.ErrorCode;
import com.lemint.tea.community.member.MemberRepository;
import com.lemint.tea.community.request.LoginRequest;
import com.lemint.tea.community.response.TokenResponse;
import com.lemint.tea.community.token.TokenService;
import com.lemint.tea.entity.Member;
import com.lemint.tea.entity.Token;
import com.lemint.tea.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.lemint.tea.util.TokenUtil.signedId;
import static com.lemint.tea.util.TokenUtil.signedRole;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {

  private final TokenUtil tokenUtil;
  private final TokenService tokenService;
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  /**
   * @apiNote login
   * @param request
   * @return token response
   * @author lemint
   * @since 2023-07-30
   * */
  @Transactional
  public TokenResponse login(LoginRequest request) {
    //사용자 확인
    Member member = memberRepository.findMemberByUserId(request.getUserId());
    if (!Objects.isNull(member)) {
      if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
        throw new CustomException(ErrorCode.BAD_PASSWORD); //비밀번호 오류
      }
    } else throw new CustomException(ErrorCode.USER_NOT_FOUND); //사용자 없음

    //기존에 존재하는 토큰 확인
    Token token = tokenService.findTokenByMemberSeq(member.getId());
    if (!Objects.isNull(token)) {
      String returnToken = token.getAccessToken();
      //발급한 token인지 확인
      Boolean checkToken = tokenService.checkToken(member.getId(), token.getAccessToken());
      if (checkToken) {
        if (tokenUtil.checkExpireTime(returnToken)) {
          //token 갱신
          returnToken = tokenUtil.createAccessToken(member.getId(), member.getUserId(), String.valueOf(signedRole));
        }
      } else throw new CustomException(ErrorCode.VALIDATED_TOKEN_ACCESS);

      return TokenResponse.builder()
          .id(member.getId())
          .role(String.valueOf(signedRole))
          .accessToken(returnToken)
          .build();
    }

    //Token 설정
    String accessToken = tokenUtil.createAccessToken(member.getId(), member.getUserId(), String.valueOf(member.getRole()));
    //토큰 저장
    tokenService.saveToken(accessToken, member.getId());

    //TreadLocal 저장
    tokenUtil.setThreadLocal(member.getId(), member.getRole(), accessToken);

    return TokenResponse.builder()
        .id(member.getId())
        .role(String.valueOf(member.getRole()))
        .accessToken(accessToken)
        .build();
  }

}
