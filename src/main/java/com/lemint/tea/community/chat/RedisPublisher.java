package com.lemint.tea.community.chat;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisPublisher {

  @Resource(name = "chatRedisTemplate")
  private final RedisTemplate<String, Object> redisTemplate;

  public void publish(ChannelTopic topic, ChatMessageDto messageDto) {
    redisTemplate.convertAndSend(topic.getTopic(), messageDto.getMessage());
  }
}
