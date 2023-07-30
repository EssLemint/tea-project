package com.lemint.tea.community.logIn;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lemint.tea.community.member.MemberRepository;
import com.lemint.tea.community.member.MemberService;
import com.lemint.tea.community.member.dto.MemberGetDto;
import com.lemint.tea.community.member.dto.MemberSaveRequest;
import com.lemint.tea.community.request.LoginRequest;
import com.lemint.tea.community.response.TokenResponse;
import com.lemint.tea.entity.Member;
import com.lemint.tea.enums.Role;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

  @Test
  @DisplayName("로그인 SUCCESS")
  void login() throws Exception {

    //given
    MemberSaveRequest request = MemberSaveRequest.builder()
        .userId("test_id")
        .password("test1234")
        .name("테스트1234")
        .build();
    memberService.saveNewMember(request);

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

}