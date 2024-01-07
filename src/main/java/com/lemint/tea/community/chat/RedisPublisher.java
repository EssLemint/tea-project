package com.lemint.tea.community.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisPublisher {

  private final RedisTemplate<String, Object> redisTemplate;
  private final ChannelTopic channelTopic;

  /**
   * 채널 토픽에 저장해준 채널로 메세지를 발행한다.
   * */
  public void publish(ChannelTopic channelTopic, ChatMessageDto chatMessageDto) {
    log.info("RedisPublisher message = {} {}", channelTopic, chatMessageDto);
    redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessageDto);
  }
}
