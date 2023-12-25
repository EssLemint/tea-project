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

  @Override
  public ChatRoom findChatRoomById(Long chatRoomId) {
    JPAQuery<ChatRoom> query = queryFactory.selectFrom(QChatRoom.chatRoom)
        .where(QChatRoom.chatRoom.id.eq(chatRoomId));

    return query.fetchOne();
  }

  @Override
  public ChatRoom getChatRoomByIds(Long senderId, Long receiveId) {
    JPAQuery<ChatRoom> query = queryFactory.selectFrom(QChatRoom.chatRoom)
        .where(QMember.member.id.eq(senderId).and(QMember.member.id.eq(receiveId)));
    return query.fetchOne();
  }

  @Override
  public ChatRoom findChatRoomByIdAndSenderId(Long chatRoomId, String senderId) {
    JPAQuery<ChatRoom> query = queryFactory.selectFrom(QChatRoom.chatRoom)
        .where(QChatRoom.chatRoom.id.eq(chatRoomId).and(QMember.member.userId.eq(senderId)));

    return query.fetchOne();
  }
}
