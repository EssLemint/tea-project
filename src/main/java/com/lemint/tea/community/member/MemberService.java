package com.lemint.tea.community.member;

import com.lemint.tea.community.member.dto.MemberGetDto;
import com.lemint.tea.community.member.dto.MemberSaveRequest;
import com.lemint.tea.community.request.MemberGetRequest;
import com.lemint.tea.community.response.MemberGetResponse;
import com.lemint.tea.entity.Member;
import com.lemint.tea.enums.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.lemint.tea.enums.Role.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

  private final MemberRepository repository;
  private final PasswordEncoder passwordEncoder;

  public MemberGetResponse findMemberByIdAndPassword(MemberGetRequest request) {
    MemberGetDto member = repository.findMemberByIdAndPassword(request.getUserId(), request.getPassword());
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
    Member member = Member.createMember(request.getUserId(), encodedPassword, request.getName(), ROLE_USER);
    Member newMember = repository.save(member);
    return newMember.getId();
  }
}
