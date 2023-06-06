package com.lemint.tea.community.logIn;

import com.lemint.tea.community.exception.CustomException;
import com.lemint.tea.community.exception.ErrorCode;
import com.lemint.tea.community.member.MemberRepository;
import com.lemint.tea.community.request.LoginRequest;
import com.lemint.tea.community.token.TokenService;
import com.lemint.tea.entity.Member;
import com.lemint.tea.util.TokenUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.lemint.tea.util.TokenUtil.signedId;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {

  private final TokenUtil tokenUtil;
  private final TokenService tokenService;
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;


  @Transactional
  public Long login(LoginRequest request) {
    //사용자 확인
    Member member = memberRepository.findMemberByUserId(request.getUserId());
    if (!Objects.isNull(member)) {
      if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
        throw new CustomException(ErrorCode.BAD_PASSWORD); //비밀번호 오류
      }
    } else throw new CustomException(ErrorCode.USER_NOT_FOUND); //사용자 없음

    //TreadLocal 저장발
    tokenUtil.setThreadLocal(member.getId(), member.getRole());
    //Token 설정
    String accessToken = tokenUtil.createAccessToken(member.getId(), member.getUserId(), String.valueOf(member.getRole()));
    //토큰 저장
    tokenService.saveToken(accessToken, member);
    return member.getId();
  }
}
