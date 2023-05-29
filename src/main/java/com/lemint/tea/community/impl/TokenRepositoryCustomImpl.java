package com.lemint.tea.community.impl;

import com.lemint.tea.community.token.TokenRepositoryCustom;
import com.lemint.tea.entity.Token;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.lemint.tea.entity.QToken.token;

@RequiredArgsConstructor
public class TokenRepositoryCustomImpl implements TokenRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Token findTokenByMemberId(Long id) {
    JPAQuery<Token> query = queryFactory.select(token)
        .from(token)
        .where(token.memberId.eq(id));

    return query.fetchOne();
  }
}
