package com.lemint.tea.tree;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/check/tree")
public class TreeController {

  /**
   * @apiNote 첫 접근 TEST
   * @param
   * @return application 접근 TEST
   * @since 2023-06-18
   * */
  @GetMapping("/check")
  String checkConnect() {
    log.info("================= CHECK CONTROLLER START=================");
    log.info("================= CHECK CONTROLLER END===================");
    return "index";
  }
}
