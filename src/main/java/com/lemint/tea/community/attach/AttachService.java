package com.lemint.tea.community.attach;

import com.lemint.tea.community.dto.BoardAttachPostRequest;
import com.lemint.tea.entity.Attach;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AttachService {

  @Transactional
  public List<Attach> attachList(List<BoardAttachPostRequest> list) {


  }
}
