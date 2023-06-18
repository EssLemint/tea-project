package com.lemint.tea.community.logIn;

import com.lemint.tea.community.request.LoginRequest;
import com.lemint.tea.community.response.TokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.lemint.tea.enums.Role.SecRoles.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LogInController {

  private final LoginService loginService;

  /**
   * @apiNote 로그인
   * @param request
   * @return 사용자 seq num
   * @since 2023-06-18
   * */
  @Secured({ANONYMOUS, USER, ADMIN, MANAGER})
  @GetMapping("/member")
  public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
    TokenResponse tokenResponse = loginService.login(request);
    return ResponseEntity.ok(tokenResponse);
  }
}
