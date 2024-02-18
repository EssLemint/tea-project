package com.lemint.tea.community.chat;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatMessageDto {

  /**
   * chatRoomId : 채팅방 번호
   * senderId : 채팅 보낸 사람
   * message : 메세지
   * */

  private Long chatRoomId;
  private Long senderId;
  private String message;

  @Builder
  public ChatMessageDto(Long chatRoomId, Long senderId, String message) {
    this.chatRoomId = chatRoomId;
    this.senderId = senderId;
    this.message = message;
  }

  @Override
  public String toString() {
    return "ChatMessageDto{" +
        "chatRoomId=" + chatRoomId +
        ", senderId=" + senderId +
        ", message='" + message + '\'' +
        '}';
  }
}
