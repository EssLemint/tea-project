package com.lemint.tea.community.chat;

import com.lemint.tea.enums.Role;
import com.lemint.tea.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * MessageChannel에 send 또는 recive의 행동이 일어나기 전의 행동을 구체화 할 수 있다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

  private final TokenUtil tokenUtil;
  Long memberId = 0L;


  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    //JWT token 확인도 가능
    log.debug("Stomp Handler");
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//    String authorizationHeader = String.valueOf(accessor.getNativeHeader("Authorization"));
//    StompCommand command = accessor.getCommand();

    //임시로 token 발급 ANONYMOUS 설정
    setAuthentication(message, accessor, Role.ROLE_ANONYMOUS);

    return message;
  }

  private void setAuthentication(Message<?> message, StompHeaderAccessor headerAccessor, Role role) {
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        role, "{noop}", AuthorityUtils.createAuthorityList(role.name())
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    log.debug("setAuthentication = {}", authentication);
    headerAccessor.setUser(authentication);
  }

  @Override
  public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
    String sessionId = accessor.getSessionId();
    switch (accessor.getCommand()) {
      case CONNECT:
        log.debug("connect = {}", sessionId); //유저가 connect 된 이후 호출
        break;
      case DISCONNECT:
        log.debug("disconnect = {}", sessionId); //유저가 disconnect 된 이후 호출
        break;
      default:
        log.debug("non connect or disconnect = {}", sessionId);
        break;
    }
  }
}
