package com.lemint.tea.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {
  /**
   * STOMP가 pub/sub 기능을 지원하지만 MessageBroker는 In-Memory로 기반이기 떄문에
   * 처리 해야할 데이터가 많아지면 서버에 과한 부담을 안겨줄 수 있다.
   * <p>
   * 이렇게에 서버의 부하 분산을 위해 Redis 사용하도록 하자
   * <p>
   * Redis도 STOMP와 같이 Publish/Subscriber 기능을 지원한다.
   * <p>
   * 구독 정보를 redis 서버에 ChannelTopic으로 저장해 같은 Topic을 구독하고 있는 사용자에게
   * 메세지를 송수신한다.
   */

  @Bean
  public RedisTemplate<String, Object> chatRedisTemplate(RedisConnectionFactory connectionFactory) {
    RedisTemplate<String, Object> chatRedisTemplate = new RedisTemplate<>();
    chatRedisTemplate.setConnectionFactory(connectionFactory);
    chatRedisTemplate.setKeySerializer(new StringRedisSerializer());
    chatRedisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));

    return chatRedisTemplate;
  }

  //redis pub/sub 메세지 처리하는 listener 설정
  @Bean
  public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    return container;
  }
}
