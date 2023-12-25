package com.lemint.tea.util;

import io.netty.handler.codec.DateFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@RequiredArgsConstructor
public class DateUtil {

  public String createNowDate() {
    LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    return convertDateToFormat(now);
  }

  private String convertDateToFormat(LocalDateTime now) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    return now.format(formatter);
  }
}
