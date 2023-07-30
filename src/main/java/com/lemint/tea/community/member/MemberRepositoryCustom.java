package com.lemint.tea.community.member;

import com.lemint.tea.community.member.dto.MemberGetDto;
import com.lemint.tea.entity.Member;

public interface MemberRepositoryCustom {
  MemberGetDto findMemberInfoByUserId(String userId);
  Member findMemberByUserId(String userId);
}
