package com.lemint.tea.common;

import com.lemint.tea.community.member.MemberService;
import com.lemint.tea.community.token.TokenService;
import com.lemint.tea.enums.Role;
import com.lemint.tea.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import static com.lemint.tea.common.ThreadToken.threadAccessToken;
import static com.lemint.tea.enums.Role.ROLE_ANONYMOUS;
import static com.lemint.tea.enums.Role.ROLE_USER;
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
  private MemberService memberService;
  @Autowired
  private TokenUtil tokenUtil;

  /**
   * @apiNote invoke authentication
   * @author KJE
   * @since 2023-07-17
   * */
  public void setAnonymous() {
    log.info("setAnonymous");
    authentication(0L, ROLE_ANONYMOUS);
  }

  /**
   * @apiNote invoke authentication
   * @author KJE
   * @since 2023-07-17
   * */
  public void setSignedUser() {
    log.info("setSignedUser");
    authentication(3L, ROLE_USER);
  }

  /**
   * @apiNote authentication set
   * @author KJE
   * @since 2023-07-17
   * */
  private void authentication(Long id, Role role) {
    log.info("create authentication");

    if (!role.equals(ROLE_ANONYMOUS)) { //사용자가 익명이 아닐시 토큰 발급
      String accessToken = tokenUtil.createAccessToken(id, "test", Role.SecRoles.USER);
      threadAccessToken.set(accessToken);
      tokenService.saveToken(accessToken, id);
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

    log.info("signedId = {}", signedId.get());
    log.info("signedRole = {}", signedRole.get());
    log.info("threadAccessToken = {}", threadAccessToken.get());
    log.info("CommonExtension SecurityContextHolder = {}", SecurityContextHolder.getContext());
  }

}
