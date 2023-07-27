package com.lemint.tea.util;

import com.lemint.tea.community.token.TokenService;
import com.lemint.tea.enums.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static com.lemint.tea.enums.Role.SecRoles.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenUtil {
  private final TokenService tokenService;
  public static final String JWT_STRING = "5f57bb1b29cc240d42d575006dc04689e4f23833b7cd7b5dd28ab60105f039cbb5b347cb6e13e98966f7eea88121b34dd5ba08272f7f537d87a0e709ff3bd7c1";
  public static final SecretKey key = Keys.hmacShaKeyFor(JWT_STRING.getBytes(StandardCharsets.UTF_8));
  public static ThreadLocal<Long> signedId = new ThreadLocal<>(); //사용자 seq저장
  public static ThreadLocal<Role> signedRole = new ThreadLocal<>(); //사용자 role 저장
  public static ThreadLocal<String> threadAccessToken = new ThreadLocal<>(); //사용자 token 저장

  /**
   * @apiNote access token 생성
   * @param id : seq, userId, role
   * @return createJwt
   * @since 2023-06-18
   * */
  public String createAccessToken(final Long id, final String userId, String role) {
    return createJwt(id, userId, role);
  }

  /**
   * @apiNote create Jwt
   * @param id : seq, userId, role
   * @return createJwt
   * @since 2023-06-18
   * */
  private String createJwt(final Long id, final String userId, String role) {
    JwtBuilder jwtBuilder = Jwts.builder()
        .setIssuer("TEA_LEMINT")  //발행인
        .claim("id", id)  //사용자 seq
        .claim("userId", userId)  //사용자 userId
        .claim("role", role)
        .setIssuedAt(new Date())
        .signWith(key);

    //600000 - 10분, 1800000 - 30분
    switch (role) {
      case USER :
        jwtBuilder.setExpiration(new Date(new Date().getTime() + 180000000));
        break;
      case MANAGER :
        jwtBuilder.setExpiration(new Date(new Date().getTime() + 540000000));
        break;
      case ADMIN :
        jwtBuilder.setExpiration(new Date(new Date().getTime() + 1080000000));
        break;
      default:
        jwtBuilder.setExpiration(new Date(new Date().getTime() + 1800000));
        break;
    }
     return jwtBuilder.compact();
  }

  /**
   * @apiNote set thread local role, id
   * @param id : seq, role
   * @return
   * @since 2023-06-18
   * */
  public void setThreadLocal(final Long id, final Role role) {
    signedRole.set(role);
    signedId.set(id);
  }

  /**
   * @apiNote decrypt jwt and check
   * @param jwt
   * @return claims of jwt
   * @since 2023-06-18
   * */
  public Claims checkJwt(final String jwt) {
    return Jwts.parser()
        .setSigningKey(key)
        .parseClaimsJws(jwt.replace("Bearer ", "").trim())
        .getBody();
  }

  /**
   * @apiNote validate access token
   * @param id : seq, jwt
   * @return true, exception on false
   * @since 2023-06-18
   * */
  public boolean validateAccessToken(final Long id, final String jwt) {
    if (!tokenService.checkToken(id, jwt)) {
      SecurityContextHolder.clearContext();
      throw new JwtException("Validate Access Token");
    }
    return true;
  }

  /**
   * @apiNote 토큰 유효기간 확인
   * @param  jwt
   * @return 만료시 true, 아닐시 false
   * @since 2023-06-18
   * */
  public boolean checkExpireTime(String jwt) {
    boolean flag = false;
    try {
      checkJwt(jwt);
    } catch (ExpiredJwtException e) {
      flag = true;
    }
    return flag;
  }
}
