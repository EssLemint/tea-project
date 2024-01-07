package com.lemint.tea.community.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Controller
/** Spring Doc
 * Applications can use annotated @Controller classes to handle messages from clients.
 * */
@RequiredArgsConstructor
public class StompController {

  private static final Set<String> SESSION_IDS = new HashSet<>();
  private final RedisTemplate<String, Object> redisTemplate;
  private final ChatService chatService;
  private final RedisMessageListenerContainer redisMessageListenerContainer;
  private final RedisPublisher redisPublisher;
  private final RedisSubscriber redisSubscriber;

  @MessageMapping("/chat/enter") //pub/chat/enter
  public void enterChat(@Payload ChatMessageDto dto) {
    log.info("ENTER_ROOM");
    ChannelTopic channelTopic = new ChannelTopic(String.valueOf(dto.getChatRoomId()));

    redisMessageListenerContainer.addMessageListener(redisSubscriber, channelTopic);
    redisPublisher.publish(channelTopic, dto);
  }

  @EventListener(SessionConnectEvent.class)
  public void onConnect(SessionConnectEvent event) {
    String sessionId = event.getMessage().getHeaders().get("simpSessionId").toString();
    SESSION_IDS.add(sessionId);
    log.info("[connect] connections : {}", SESSION_IDS.size());
  }

  @EventListener(SessionDisconnectEvent.class)
  public void webSocketDisconnectListener(SessionDisconnectEvent event) {
    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

    String senderId = (String) headerAccessor.getSessionAttributes().get("senderId");
    String chatRoomId = (String) headerAccessor.getSessionAttributes().get("chatRoomId");

    chatService.minusUserCount(chatRoomId);
    String name = chatService.getUserNameById(senderId);
    chatService.deleteUserFromChatRoom(chatRoomId, senderId); //사용자 접근을 어떻게 제한하는 로직을 진행해야하는지....

    if (!Objects.isNull(name)) {
      log.info("User Disconnected : " + name);

      ChatMessageDto chatMessageDto = ChatMessageDto.builder()
          .chatRoomId(Long.parseLong(chatRoomId))
          .senderId(Long.parseLong(senderId))
          .message(name + "퇴장하셨습니다.")
          .build();

      redisTemplate.convertAndSend("/sub/chatroom/detail/" + chatRoomId, chatMessageDto);
    }
  }
}
