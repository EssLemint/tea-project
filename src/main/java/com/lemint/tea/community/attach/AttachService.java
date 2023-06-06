package com.lemint.tea.community.attach;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AttachService {
  /**
   * 첨부 파일 저장
   * */


  private final AttachRepository attachRepository;
}
