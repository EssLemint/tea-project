package com.lemint.tea.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lemint.tea.community.token.TokenService;
import com.lemint.tea.entity.Member;
import com.lemint.tea.enums.Role;
import com.lemint.tea.util.TokenUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.Header;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.net.http.HttpRequest;

import static com.lemint.tea.common.ThreadToken.threadAccessToken;
import static com.lemint.tea.util.TokenUtil.signedId;
import static com.lemint.tea.util.TokenUtil.signedRole;

@Slf4j
@SpringBootTest(properties = "spring.config.location=classpath:application-test.yml")
@Transactional
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommonExtension {
  @Autowired
  private TokenService tokenService;
  @Autowired
  private TokenUtil tokenUtil;

  /**
   * @apiNote invoke authentication
   * @author KJE
   * @since 2023-07-17
   * */
  public void setSignedUser() {
    log.info("setSignedUser");
    authentication(3L, "test", Role.ROLE_USER);
  }

  /**
   * @apiNote authentication set
   * @author KJE
   * @since 2023-07-17
   * */
  private void authentication(Long id, String userId, Role role) {
    log.info("create authentication");

    if (!role.equals(Role.ROLE_ANONYMOUS)) {
      String accessToken = tokenUtil.createAccessToken(id, userId, role.name());
      Member member = Member.createMember(userId, "test1234", "teatname", role);
      tokenService.saveToken(accessToken, member);
      log.info("accessToken = {}", accessToken);
      threadAccessToken.set(accessToken);
//      setAuthTokenHeader(accessToken);
    }

    signedId.set(id);
    signedRole.set(role);

    SecurityContextHolder
        .getContext()
        .setAuthentication(
        new UsernamePasswordAuthenticationToken(
            id, "{noop}", AuthorityUtils.createAuthorityList(role.name())
        )
    );

    log.info("id = {}", id);
    log.info("role = {}", role);
    log.info("SecurityContextHolder = {}", SecurityContextHolder.getContext());
  }

}
