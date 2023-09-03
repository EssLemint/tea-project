package com.lemint.tea.community.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lemint.tea.common.CommonExtension;
import com.lemint.tea.community.board.BoardService;
import com.lemint.tea.community.logIn.LoginService;
import com.lemint.tea.community.request.BoardPostRequest;
import com.lemint.tea.community.request.LoginRequest;
import com.lemint.tea.community.request.MemberGetRequest;
import com.lemint.tea.community.response.MemberGetResponse;
import com.lemint.tea.community.token.TokenService;
import com.lemint.tea.entity.Token;
import com.lemint.tea.enums.Role;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
class MemberControllerTest extends CommonExtension {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  EntityManager entityManager;

  @Autowired
  MemberService service;
  @Autowired
  TokenService tokenService;

  @BeforeEach
  public void setUp(WebApplicationContext context) {
    mockMvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .addFilter(new CharacterEncodingFilter("UTF-8", true))
        .build();
  }


  @Test
  @DisplayName("사용자 정보 조회 단건")
  void findMemberByIdAndPassword() throws Exception {
    String token = tempLogin();

    MvcResult mvcResult = mockMvc.perform(get("/member/get")
            .header("Authorization", token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(
                MemberGetRequest.builder()
                    .userId("test")
                    .password("testpassword1234")
                    .build()))).andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    String content = mvcResult.getResponse().getContentAsString();
    MemberGetResponse response = objectMapper.readValue(content, MemberGetResponse.class);
    log.info("content = {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(content));
  }

  private String tempLogin() throws Exception {
    Long memberSeq = 3L;
    String id = "test";
    String pwd = "testpassword1234";

    Token token = tokenService.findTokenByMemberSeq(memberSeq);
    return token.getAccessToken();
  }

  @Test
  void createMember() {
  }
}