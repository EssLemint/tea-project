package com.lemint.tea.community.impl;

import com.lemint.tea.community.BoardCustomRepository;
import com.lemint.tea.community.dto.BoardAttachPostRequest;
import com.lemint.tea.community.dto.BoardPostRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BoardCustomRepositoryImpl implements BoardCustomRepository {

  private final JPAQueryFactory queryFactory;


}
