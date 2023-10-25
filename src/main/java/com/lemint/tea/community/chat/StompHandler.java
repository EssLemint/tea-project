package com.lemint.tea.community.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {


  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    //JWT token 확인도 가능
    log.debug("Stomp Handler");
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
    if (accessor.getCommand() == StompCommand.CONNECT) {
      String authorization = accessor.getFirstNativeHeader("Authorization");
      //TODO Token 검증

    }
    return message;
  }

  @Override
  public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
    String sessionId = accessor.getSessionId();
    switch (accessor.getCommand()) {
      case CONNECT :
        log.debug("connect"); //유저가 connect 된 이후 호출
        break;
      case DISCONNECT:
        log.debug("disconnect"); //유저가 disconnect 된 이후 호출
        break;
      default:
        log.debug("default");
        break;
    }
  }
}
