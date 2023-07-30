package com.lemint.tea.community.member.impl;

import com.lemint.tea.community.member.MemberRepositoryCustom;
import com.lemint.tea.community.member.dto.MemberGetDto;
import com.lemint.tea.entity.Member;
import com.lemint.tea.entity.QMember;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QMap;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.lemint.tea.entity.QMember.*;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public MemberGetDto findMemberInfoByUserId(String userId) {
    JPAQuery<MemberGetDto> query = queryFactory.select(Projections.fields(MemberGetDto.class
            , member.id
            , member.name
            , member.password
        )).from(member)
        .where(member.userId.eq(userId));
    return query.fetchOne();
  }

  @Override
  public Member findMemberByUserId(String userId) {
    JPAQuery<Member> query = queryFactory
        .selectFrom(member)
        .where(member.userId.eq(userId));

    return query.fetchOne();
  }
}
