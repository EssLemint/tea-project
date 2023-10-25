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
@Table(name = "TB_CHATROOM")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends BaseEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @Comment("채팅방 번호")
  private Long id;

  @Comment("채팅방 이름")
  @Column(length = 30)
  private String name;

  @ManyToOne
  @JoinColumn(name = "sender_id")
  private Member senderId;

  @ManyToOne
  @JoinColumn(name = "receive_id")
  private Member receiveId;


  private ChatRoom(Member senderId, Member receiveId) {
    this.senderId = senderId;
    this.receiveId = receiveId;
  }

  public static ChatRoom createChatRoom(Member senderId, Member receiveId) {
    return new ChatRoom(senderId, receiveId);
  }

  public void updateChatRoom(String name) {
    this.name = name;
  }

  public void removeChatRoom() {
    remove();
  }

}
