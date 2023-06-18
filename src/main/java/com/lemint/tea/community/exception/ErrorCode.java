package com.lemint.tea.community.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

  //== 200 ==//
  SUCCESS(HttpStatus.OK, "OK"),

  //== 400 ==//
  NOT_SUPPORTED_HTTP_METHOD(HttpStatus.BAD_REQUEST,"Http Method Failed"),
  NOT_VALID_METHOD_ARGUMENT(HttpStatus.BAD_REQUEST,"REQUEST를 확인해주세요"),
  BAD_PASSWORD(HttpStatus.BAD_REQUEST,"비밀번호가 일치하지 않습니다."),
  USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 사용자를 찾을 수 없습니다."),

  //== Token == //
  VALIDATED_TOKEN_ACCESS(HttpStatus.NOT_ACCEPTABLE, "위변조된 토큰입니다.");

  private final HttpStatus status;
  private final String message;
  }
