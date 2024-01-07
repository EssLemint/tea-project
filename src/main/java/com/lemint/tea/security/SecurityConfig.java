package com.lemint.tea.security;

import com.lemint.tea.community.token.TokenConfig;
import com.lemint.tea.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
@EnableWebSecurity(debug = true) //개발용 True
public class SecurityConfig {
  private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
  private final TokenUtil tokenUtil;

  /**
   * Spring Boot 3.x.x , security 6.x.x 부터 명령어들이 lambda로 변경된 사항들이 존재함.
   * 재작업 진행 > 기록 남기기
   * */
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    log.info("================= SecurityConfig =================  START =================");
    http
        .headers().frameOptions().sameOrigin() //sockJS는 기본적으로 HTML Iframe 요소를 통한 전공을 허용하지 않도록 설정되는데 해당 내용을 해제한다.
        .and().csrf(AbstractHttpConfigurer::disable)
        .cors()
        .and()
        .formLogin(formLogin -> formLogin
            .loginPage("/login")
            .permitAll()
        )
        .sessionManagement((sessionManagement) ->
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .authorizeHttpRequests((authorizeRequests) ->
            authorizeRequests.anyRequest().permitAll()
        )
        .apply(new TokenConfig(tokenUtil));

    log.info("================= SecurityConfig =================  END =================");
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return web -> web.ignoring().requestMatchers("/static/**");
  }
}

