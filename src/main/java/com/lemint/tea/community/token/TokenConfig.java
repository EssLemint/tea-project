package com.lemint.tea.community.token;

import com.lemint.tea.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Slf4j
@RequiredArgsConstructor
public class TokenConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private final TokenUtil tokenUtil;

  @Override
  public void configure(HttpSecurity http) throws Exception {
  }
}
