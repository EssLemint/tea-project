package com.lemint.tea.community.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class CustomErrorController implements ErrorController {

  @GetMapping("/error")
  public String goErrorPage() {
    return "index";
  }
}
