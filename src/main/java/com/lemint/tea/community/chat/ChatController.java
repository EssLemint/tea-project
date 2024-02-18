package com.lemint.tea.community.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
/** Spring Doc
 * Applications can use annotated @Controller classes to handle messages from clients.
 * */
@Controller
@RequiredArgsConstructor
/**
 * Chat에 대한 Controller
 * */
public class ChatController {

  private static final Set<String> SESSION_IDS = new HashSet<>();
  private final RedisTemplate<String, Object> redisTemplate;
  private final ChatService chatService;
  private final ChatRoomService chatRoomService;
  private final RedisMessageListenerContainer redisMessageListenerContainer;
  private final RedisPublisher redisPublisher;
  private final RedisSubscriber redisSubscriber;


}
