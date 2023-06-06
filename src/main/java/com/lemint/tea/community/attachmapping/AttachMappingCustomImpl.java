package com.lemint.tea.community.attachmapping;

import com.lemint.tea.entity.BoardAttachMapping;
import com.lemint.tea.entity.QBoardAttachMapping;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.lemint.tea.entity.QBoardAttachMapping.*;

@RequiredArgsConstructor
public class AttachMappingCustomImpl implements AttachMappingCustom{

  private final JPAQueryFactory queryFactory;

  @Override
  public List<BoardAttachMapping> findAttachMappingByBoardId(Long id) {
    JPAQuery<BoardAttachMapping> query = queryFactory.selectFrom(boardAttachMapping)
        .where(boardAttachMapping.board.id.eq(id));

    return query.fetch();
  }
}
