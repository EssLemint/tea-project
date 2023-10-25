package com.lemint.tea.config;

import com.lemint.tea.community.chat.StompHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * 일반 websocket을 사용하는게 아닌 spring에서 지원해주는 STOMP을 사용할 예정
 * websocket으로 가능은 하지만 어떤 들어오는 요청이 어떤 채팅인지 어떻게 처리해야하는지 별도 로직을 요구 한다.
 * STOMP : Simple Text Oriented Messageing Protocol
 * 스프리에서 지원하고 외부 메세지 브로커의 사용 없이 Simple-In-Memory-Broker을 이용하고
 * subscribe 중인 다른 client에게 메세지 전송이 가능하다.
 * */
@Slf4j
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  private final StompHandler stompHandler;


  //sockJS Fallback을 이용해 노출할 endpoint 설정
  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    log.info("end point 선언");
    //웹소캣이 handshake 하기 위해 연결하는 end point
    registry.addEndpoint("/ws/chat")
        .setAllowedOrigins("*")
        .withSockJS();

    //withSockJS(): WebSocket을 지원하지 않는 브라우저에서 HTTP의 Polling과 같은 방식으로 websocket의 요청을 수행하도록 도와준다.
    //SockJS를 사용하는 경우, 클라이언트에서 WebSocket 요청을 보낼 때 설정한 엔드포인트 뒤에 /webSocket를 추가해야 정상 작동 된다.
  }

  //메세지 브로커에 관한 설정
  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    //안에서 정의한 값이 prefix로 부터 붙은 메세지가 송신 되었을때 MessageBroker가 처리한다.
    //queue는 1:1 topic은 1:N
    registry.enableSimpleBroker("/sub"); //받는사람

    //메세지가 상황에 따라서 가공이 필요할때 handler을 탈 수 있는데 이때 /app을 달고오면 핸들러로 전달 된다.
    registry.setApplicationDestinationPrefixes("/pub"); //보내는 사람
  }

  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    //session의 connect, disconnect의 시점을 알고 싶다면
    registration.interceptors(stompHandler);
  }
}
