package com.lemint.tea.community.chat;

import com.lemint.tea.community.member.MemberRepository;
import com.lemint.tea.entity.ChatRoom;
import com.lemint.tea.entity.Chats;
import com.lemint.tea.entity.Member;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
/**
 * Chat : 채팅에 대한 SERVICE
 * */
public class ChatService {

  private final RedisTemplate<String, Object> redisTemplate;
  private final ChatRoomRepository chatRoomRepository;
  private final MemberRepository memberRepository;
  private final ChatsRepository chatsRepository;


}
