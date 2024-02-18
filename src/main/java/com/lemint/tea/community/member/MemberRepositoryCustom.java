package com.lemint.tea.community.member;

import com.lemint.tea.community.member.dto.MemberGetDto;
import com.lemint.tea.entity.ChatRoom;
import com.lemint.tea.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {
  MemberGetDto findMemberInfoByUserId(String userId);
  Member findMemberByUserId(String userId);
}
