package com.lemint.tea.community.board.impl;

import com.lemint.tea.community.board.BoardCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardCustomRepositoryImpl implements BoardCustomRepository {
  private final JPAQueryFactory queryFactory;
}
