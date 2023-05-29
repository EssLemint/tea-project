package com.lemint.tea.community.logIn;

import com.lemint.tea.community.request.LoginRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LogInController {

  private final LoginService loginService;

  @GetMapping("/member")
  public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
    Long memberSeq = loginService.login(request);
    return ResponseEntity.ok(memberSeq);
  }
}
