package com.lemint.tea.config;

import com.lemint.tea.community.exception.CustomException;
import com.lemint.tea.community.exception.ErrorCode;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StringUtils;
import redis.embedded.RedisServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Slf4j
@Profile("local")
@Configuration
public class EmbeddedRedisConfig {

  @Value("${spring.data.redis.port}")
  private int redisPort;

  private RedisServer redisServer;

  @PostConstruct
  public void redisServer(){
    RedisServer redisServer = RedisServer.builder()
        .port(redisPort)
        .setting("maxmemory 126M")
        .build();

  }

  @PreDestroy
  public void stopRedis(){
    if(redisServer != null){
      redisServer.stop();
    }
  }

  private boolean isRedisRunning(Process process) {
    String line;
    StringBuilder pidInfo = new StringBuilder();

    try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
      while (((line = input.readLine()) != null)) {
        pidInfo.append(line);
      }
    } catch (Exception e) {
      throw new CustomException(ErrorCode.REDIS_ERROR_ON_SET_UP);
    }

    return StringUtils.hasText(pidInfo.toString());
  }

}
