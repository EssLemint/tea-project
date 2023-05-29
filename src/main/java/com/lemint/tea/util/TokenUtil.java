package com.lemint.tea.util;

import com.lemint.tea.community.token.TokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenUtil {

  private final TokenService tokenService;

  public static final String JWT_STRING = "5f57bb1b29cc240d42d575006dc04689e4f23833b7cd7b5dd28ab60105f039cbb5b347cb6e13e98966f7eea88121b34dd5ba08272f7f537d87a0e709ff3bd7c1";
  public static final SecretKey key = Keys.hmacShaKeyFor(JWT_STRING.getBytes(StandardCharsets.UTF_8));
  public static ThreadLocal<Long> signedId = new ThreadLocal<>(); //사용자 seq저장
  //ROLE 추후 추가


  public String createAccessToken(final Long id, final String userId) {
    return createJwt(id, userId);
  }

  public String createJwt(final Long id, final String userId) {
    return Jwts.builder()
        .setIssuer("TEA_LEMINT")  //발행인
        .claim("id", id)  //사용자 seq
        .claim("userId", userId)  //사용자 userId
        .setIssuedAt(new Date())
        .signWith(key)
        .compact();
  }

  public void setSignedId(final Long id) {
    signedId.set(id);
  }


}
