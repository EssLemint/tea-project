package com.lemint.tea.community.chat;

import com.lemint.tea.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

  private final TokenUtil tokenUtil;

  @GetMapping("")
  public String enterPage() {
    return "chat/room";
  }
}
