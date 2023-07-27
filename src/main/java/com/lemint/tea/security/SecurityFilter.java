package com.lemint.tea.security;

import com.lemint.tea.enums.Role;
import com.lemint.tea.util.TokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.lang.Strings;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.lemint.tea.util.TokenUtil.*;
import static io.jsonwebtoken.lang.Strings.hasLength;
import static java.util.Optional.ofNullable;

@Slf4j
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

  private TokenUtil tokenUtil;
  public SecurityFilter(TokenUtil tokenUtil) {
    this.tokenUtil = tokenUtil;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    log.info("==================== SecurityFilter START ====================");
    log.info("request = {}", request.getHeader("Authorization"));
    try {
      String authorization = request.getHeader("Authorization");
      Long id = 0L;
      Role role = Role.ROLE_ANONYMOUS;
      String accessToken = "";

      if (hasLength(authorization)) {
        accessToken = authorization.replace("Bearer ", "");

        Claims claims = tokenUtil.checkJwt(authorization);
        id = Long.parseLong(String.valueOf(claims.get("id")));
        role = Role.valueOf(String.valueOf(claims.get("role")));
      }

      threadAccessToken.set(accessToken);
      signedId.set(id);
      signedRole.set(role);

      Role roleauth = signedRole.get();
      SecurityContextHolder.getContext().setAuthentication(
          new UsernamePasswordAuthenticationToken(
              roleauth, "{noop}", AuthorityUtils.createAuthorityList(roleauth.name())
          )
      );

      tokenUtil.setThreadLocal(id, role);

      log.info("SecurityContextHolder = {}", SecurityContextHolder.getContext().getAuthentication());
      log.info("id = {}", id);
      log.info("role = {}", role);
      log.info("================ SecurityFilter END ================");

      filterChain.doFilter(request, response);
    } catch (Exception e) {
      SecurityContextHolder.clearContext();
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      log.info("ExpiredJwtException error = {}", e.getMessage());
    }

  }
}
