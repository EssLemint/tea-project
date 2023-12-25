package com.lemint.tea.community.chat;

import com.lemint.tea.community.member.MemberRepository;
import com.lemint.tea.entity.ChatRoom;
import com.lemint.tea.entity.Member;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

  private final ChatRoomRepository chatRoomRepository;
  private final MemberRepository memberRepository;

  public void addUserCount(Long chatRoomId) {
    ChatRoom room = chatRoomRepository.findChatRoomById(chatRoomId);
    room.addMemberCount();
  }

  public void minusUserCount(String chatRoomId) {
    ChatRoom room = chatRoomRepository.findChatRoomById(Long.parseLong(chatRoomId));
    room.minusUserCount();
  }

  public ChatRoom getChatRoomByIds(Long senderId, Long receiveId) {
    ChatRoom room = chatRoomRepository.getChatRoomByIds(senderId, receiveId);
    return room;
  }

  public ChatRoom createNewChatRoom(ChatMessageDto dto) {
    Member sender = memberRepository.findById(dto.getSenderId()).orElseThrow(EntityNotFoundException::new);
    Member receiver = memberRepository.findById(dto.getReceiveId()).orElseThrow(EntityNotFoundException::new);
    ChatRoom entity = ChatRoom.createChatRoom(sender, receiver);

    chatRoomRepository.save(entity);
    return entity;
  }

  public String getUserNameById(String senderId) {
    Member member = memberRepository.findMemberByUserId(senderId);
    return member.getName();
  }

  public void deleteUserFromChatRoom(String chatRoomId, String senderId) {
    ChatRoom room = chatRoomRepository.findChatRoomByIdAndSenderId(Long.parseLong(chatRoomId), senderId);
    room.removeChatRoom();
  }
}
