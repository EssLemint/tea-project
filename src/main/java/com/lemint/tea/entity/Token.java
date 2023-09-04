package com.lemint.tea.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;

@Slf4j
@Entity
@Table(name = "TB_TOKEN")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Token {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Comment("토큰 일련 번호")
  @Column(name = "TOKEN_ID")
  private Long id;

  @Comment("멤버 일련 번호")
  private Long memberId;

  @Comment("접속 토큰")
  @Column(columnDefinition = "LONGTEXT")
  private String accessToken;

  @Comment("리프레시 토큰")
  @Column(columnDefinition = "LONGTEXT")
  private String refreshToken;

  @CreationTimestamp
  @Comment("토큰 생성 날짜")
  private LocalDateTime createAt;

  @Comment("토큰 갱신 날짜")
  @UpdateTimestamp
  private LocalDateTime updateAt;

  private Token(Long memberId, String accessToken, String refreshToken) {
    this.memberId = memberId;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

  public static Token createToken(Long memberId, String accessToken, String refreshToken) {
    return new Token(memberId, accessToken, refreshToken);
  }

  public void updateAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public void updateRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }



}
