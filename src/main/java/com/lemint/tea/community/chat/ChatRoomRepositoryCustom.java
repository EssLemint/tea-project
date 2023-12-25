package com.lemint.tea.community.chat;

import com.lemint.tea.entity.ChatRoom;

public interface ChatRoomRepositoryCustom {
  ChatRoom findChatRoomById(Long chatRoomId);

  ChatRoom getChatRoomByIds(Long senderId, Long receiveId);

  ChatRoom findChatRoomByIdAndSenderId(Long chatRoomId, String senderId);
}
