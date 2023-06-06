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

    try {
      String authorization = request.getHeader("Authorization");
      String jwt = ofNullable(authorization).orElse(null);
      Long id = 0L;
      Role role = Role.ROLE_ANONYMOUS;

      if (Strings.hasLength(jwt)) {
        Claims claims = tokenUtil.checkJwt(jwt);
        id = Long.parseLong(String.valueOf(claims.get("userId")));
        role = Role.valueOf(String.valueOf(claims.get("role")));

        tokenUtil.validateAccessToken(id, jwt.replace("Bearer ", "").trim());
      }
      SecurityContextHolder.getContext().setAuthentication(
          new UsernamePasswordAuthenticationToken(
              id, "{noop}", AuthorityUtils.createAuthorityList(role.name())
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
