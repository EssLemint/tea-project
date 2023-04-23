package com.lemint.tea.tree;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("tree")
public class TreeController {

  @GetMapping("/check")
  String checkConnect() {
    log.info("================= CHECK CONTROLLER START=================");

    log.info("================= CHECK CONTROLLER END=================");

    return "index";
  }
}
