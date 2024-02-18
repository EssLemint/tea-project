package com.lemint.tea.community.chat;

import com.lemint.tea.community.member.MemberRepository;
import com.lemint.tea.entity.ChatRoom;
import com.lemint.tea.entity.Chats;
import com.lemint.tea.entity.Member;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {
  private final ChatRoomRepository chatRoomRepository;
  private final ChatsRepository chatsRepository;
  private final MemberRepository memberRepository;

  private final RedisPublisher redisPublisher;
  private final RedisSubscriber redisSubscriber;

  private static final String MESSAGE_ROOM = "MESSAGE_ROOM";
  private final RedisTemplate<String, Object> redisTemplate;
  private HashOperations<String, String, ChatRoomDto> hashOperations;

  private Map<String, ChannelTopic> topics;

  @PostConstruct
  private void init() {
    hashOperations = redisTemplate.opsForHash();
    topics = new HashMap<>();
  }

}
