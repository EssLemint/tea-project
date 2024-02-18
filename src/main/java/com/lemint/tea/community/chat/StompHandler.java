package com.lemint.tea.community.chat;

import com.lemint.tea.community.exception.CustomException;
import com.lemint.tea.community.exception.ErrorCode;
import com.lemint.tea.enums.Role;
import com.lemint.tea.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

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

    try {
      //JWT token 확인도 가능
      StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
      log.info("accessor = {}", accessor);

      if (accessor.getCommand() == StompCommand.CONNECT) {
        String authorization = accessor.getFirstNativeHeader("Authorization");
        if (!Objects.isNull(authorization) && !tokenUtil.validateAccessTokenByJwt(authorization)) {
          throw new CustomException(ErrorCode.VALIDATED_TOKEN_ACCESS);
        }

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(Role.USER, "{noop}", AuthorityUtils.createAuthorityList(Role.USER.name()));

        if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        log.info("SecurityContextHolder = {}", SecurityContextHolder.getContext().getAuthentication());
        accessor.setUser(authenticationToken);
        log.info("accessor setUser = {}", accessor);
      }
      return message;
    } catch (NullPointerException e) {
      throw new CustomException(ErrorCode.SERVER_ERROR);
    }
  }


  @Override
  public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
    String sessionId = accessor.getSessionId();
    switch (accessor.getCommand()) {
      case CONNECT:
        log.info("connect = {}", sessionId); //유저가 connect 된 이후 호출
        break;
      case DISCONNECT:
        log.info("disconnect = {}", sessionId); //유저가 disconnect 된 이후 호출
        break;
      default:
        log.info("non connect or disconnect = {}", sessionId);
        break;
    }
  }
}
