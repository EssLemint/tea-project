package com.lemint.tea.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Slf4j
@Configuration
//@EnableWebSocketSecurity csrf에 대한 추가 조작이 update 되면 적용 예정, 현재는 legacy로 적용 중
public class SecurityWebSocketConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

  @Override
  protected void configureInbound(MessageSecurityMetadataSourceRegistry message) {
    message
        .nullDestMatcher().permitAll()
        .simpDestMatchers("/pub/**").authenticated()
        .simpSubscribeDestMatchers("/sub/**").authenticated()
        .anyMessage().denyAll();
  }

  @Override
  protected boolean sameOriginDisabled() {
    return true;
  }

  /**
   * At this point, CSRF is not configurable when using @EnableWebSocketSecurity,
   * though this will likely be added in a future release.
   *
   *   @Bean
   *   AuthorizationManager<Message < ?>> authorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
   *     messages
   *         .simpTypeMatchers(SimpMessageType.CONNECT).authenticated() // 웹소켓 연결시에만 인증 확인(인증 정보는 WebSecurity(ex. formLogin)를 통해 인증한 정보를 활용)
   *         .anyMessage().permitAll()
   *         .build();
   *     return messages.build();
   *
   *       @Bean("csrfChannelInterceptor") // for disable csrf
   *   public ChannelInterceptor csrfChannelInterceptor() {
   *     return new ChannelInterceptor() {
   *     };
   *   }
   *   }
   * */


}
