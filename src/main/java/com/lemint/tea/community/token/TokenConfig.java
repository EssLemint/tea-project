package com.lemint.tea.community.token;

import com.lemint.tea.security.SecurityFilter;
import com.lemint.tea.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@RequiredArgsConstructor
public class TokenConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  //Service와 tokenUtil 연결
  private final TokenUtil tokenUtil;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    SecurityFilter filter = new SecurityFilter(tokenUtil);

    http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
  }
}
