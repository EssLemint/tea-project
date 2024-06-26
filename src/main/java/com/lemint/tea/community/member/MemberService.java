package com.lemint.tea.community.member;

import com.lemint.tea.community.exception.CustomException;
import com.lemint.tea.community.exception.ErrorCode;
import com.lemint.tea.community.member.dto.MemberFindPwdRequest;
import com.lemint.tea.community.member.dto.MemberGetDto;
import com.lemint.tea.community.member.dto.MemberSaveRequest;
import com.lemint.tea.community.request.MemberGetRequest;
import com.lemint.tea.community.response.MemberGetResponse;
import com.lemint.tea.entity.Member;
import com.lemint.tea.enums.Role;
import com.lemint.tea.util.TempPasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Objects;

import static com.lemint.tea.enums.Role.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

  private final MemberRepository repository;
  private final PasswordEncoder passwordEncoder; //디코딩 지원이 안되는구나;; 변경이 필요
  private final TempPasswordUtil pwdUtil;

  public MemberGetResponse findMemberInfoByUserId(MemberGetRequest request) {
    MemberGetDto member = repository.findMemberInfoByUserId(request.getUserId());

    if (Objects.isNull(member)) {
      throw new CustomException(ErrorCode.USER_NOT_FOUND);
    }

    if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
      throw new CustomException(ErrorCode.BAD_PASSWORD);
    }

    MemberGetResponse response = MemberGetResponse.builder()
        .id(member.getId())
        .name(member.getName())
        .build();

    return response;
  }

  @Transactional
  public Long saveNewMember(MemberSaveRequest request) {
    String encodedPassword = passwordEncoder.encode(request.getPassword());
    //기본 사용자는 USER로 생성한다.
    Member member = Member.createMember(request.getUserId(), encodedPassword, request.getName(), USER);
    Member newMember = repository.save(member);
    return newMember.getId();
  }


  public String findPassword(MemberFindPwdRequest request) {
    Member member = repository.findMemberByUserId(request.getUserId());
    if (Objects.isNull(member)) {
      throw new CustomException(ErrorCode.USER_NOT_FOUND);
    }

    //임시 비밀번호는 우선 10자
    String randomPassword = pwdUtil.getRandomPassword(10);
    member.updateTempPassword(randomPassword);
    return randomPassword;
  }
}
