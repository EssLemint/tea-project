package com.lemint.tea.community.chat;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

  private final ObjectMapper objectMapper;
  private final SimpMessageSendingOperations sendingOperations;
  private final RedisTemplate<String, Object> redisTemplate;

  /**
   * redis에서 메세지가 발행(publish)되면 대기하고 있던 onMessage가 해당 메세지를 받아서 처리한다.
   * */
//  public void sendMessage(String publishMessage) {
//    try {
//      ChatMessageDto messageDto = objectMapper.readValue(publishMessage, ChatMessageDto.class);
//
//      //구독자들에게 send
//      sendingOperations.convertAndSend("/sub/chat/room" + messageDto.getChatRoomId(), messageDto);
//    } catch (Exception e) {
//      log.error(e.getMessage());
//    }
//  }

  @Override
  public void onMessage(Message message, byte[] pattern) {
    try {
      String body = redisTemplate.getStringSerializer().deserialize(message.getBody());
      ChatMessageDto messageDto = objectMapper.readValue(body, ChatMessageDto.class);
      log.info("onMessage = {}", messageDto);
      sendingOperations.convertAndSend("/sub/chat/room/" + messageDto.getChatRoomId(), messageDto);
    } catch (Exception e) {
      log.error("on message exception = {}", e);
    }

  }
}
