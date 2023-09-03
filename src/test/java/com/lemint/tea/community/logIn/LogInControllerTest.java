package com.lemint.tea.community.logIn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lemint.tea.community.exception.CustomException;
import com.lemint.tea.community.member.MemberService;
import com.lemint.tea.community.member.dto.MemberSaveRequest;
import com.lemint.tea.community.request.LoginRequest;
import com.lemint.tea.community.response.TokenResponse;
import com.lemint.tea.enums.Role;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static com.lemint.tea.common.ThreadToken.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@SpringBootTest(properties = "spring.config.location=classpath:application-test.yml")
@Transactional
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LogInControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  EntityManager entityManager;

  @Autowired
  private MemberService memberService;
  @Autowired
  private LoginService loginService;

  @BeforeEach
  public void setUp(WebApplicationContext context) {
    mockMvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .addFilter(new CharacterEncodingFilter("UTF-8", true))
        .build();
  }

  @BeforeEach
  public void setUpMember() {
    MemberSaveRequest request = MemberSaveRequest.builder()
        .userId("test_id")
        .password("test1234")
        .name("테스트1234")
        .build();
    memberService.saveNewMember(request);

    removeThreadLocal();
  }

  public void removeThreadLocal() {
    signedId.remove();
    signedRole.remove();
    threadAccessToken.remove();
  }

  @Test
  @DisplayName("로그인 SUCCESS")
  void login() throws Exception {

    //given
    String id = "test_id";
    String pwd = "test1234";

    //when
    MvcResult mvcResult = mockMvc.perform(get("/login/member")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(
                LoginRequest.builder()
                    .userId(id)
                    .password(pwd)
                    .build()))).andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    String content = mvcResult.getResponse().getContentAsString();
    TokenResponse response = objectMapper.readValue(content, TokenResponse.class);
    log.info("content = {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(
        content
    ));
  }


  @Test
  @DisplayName("로그인 실패 : wrong password")
  void fail_login() throws Exception {

    String id = "test_id";
    String pwd = "wrong_password_1234";

    assertThrows(CustomException.class, () -> {
      loginService.login(LoginRequest.builder()
          .userId(id)
          .password(pwd)
          .build());
    });
  }


  @Test
  @DisplayName("로그인 실패 member not found")
  void fail_login_not_found() {

    String id = "member_not_found";
    String pwd = "test1234";

    CustomException customException = assertThrows(CustomException.class, () -> {
      loginService.login(LoginRequest.builder()
          .userId(id)
          .password(pwd)
          .build());
    });

    assertEquals(HttpStatus.BAD_REQUEST, customException.getErrorCode().getStatus());
  }


}