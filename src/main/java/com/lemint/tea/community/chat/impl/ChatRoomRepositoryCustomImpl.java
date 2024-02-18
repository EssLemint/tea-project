package com.lemint.tea.community.chat.impl;

import com.lemint.tea.community.chat.ChatRoomRepositoryCustom;
import com.lemint.tea.entity.ChatRoom;
import com.lemint.tea.entity.QChatRoom;
import com.lemint.tea.entity.QMember;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChatRoomRepositoryCustomImpl implements ChatRoomRepositoryCustom {

  private final JPAQueryFactory queryFactory;

}
