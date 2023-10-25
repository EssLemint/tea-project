package com.lemint.tea.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Slf4j
@Entity
@Getter
@Table(name = "TB_CHATS")
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chats extends BaseEntity{


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Comment("SEQ")
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "chatRoom_id")
  private ChatRoom chatRoomId;

  @ManyToOne
  @JoinColumn(name = "sender_id")
  private Member senderId;

  @Lob
  @Column(name = "chat_message")
  private String message;

  private Chats(ChatRoom chatRoomId, Member senderId, String message) {
    this.chatRoomId = chatRoomId;
    this.senderId = senderId;
    this.message = message;
  }

  public static Chats createChats(ChatRoom chatRoomId, Member senderId, String message) {
    return new Chats(chatRoomId, senderId, message);
  }

  public void removeChats() {
    remove();
  }
}
