package com.lemint.tea.community.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lemint.tea.common.CommonExtension;
import com.lemint.tea.community.board.BoardService;
import com.lemint.tea.community.request.BoardPostRequest;
import com.lemint.tea.community.request.MemberGetRequest;
import com.lemint.tea.community.response.MemberGetResponse;
import com.lemint.tea.enums.Role;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
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

  @BeforeEach
  public void setUp(WebApplicationContext context) {
    mockMvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .addFilter(new CharacterEncodingFilter("UTF-8", true))
        .build();
  }


  @Test
  @WithMockUser
  void findMemberByIdAndPassword() throws Exception {
    setSignedUser();

    MvcResult mvcResult = mockMvc.perform(get("/member/get")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(
                MemberGetRequest.builder()
                    .userId("test")
                    .password("testpassword1234")
                    .build()))
            .with(csrf())).andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    String content = mvcResult.getResponse().getContentAsString();
    MemberGetResponse response = objectMapper.readValue(content, MemberGetResponse.class);
    log.info("content = {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(content));
  }

  @Test
  void createMember() {
  }
}