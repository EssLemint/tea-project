package com.lemint.tea.community;

import com.lemint.tea.community.dto.BoardAttachPostRequest;
import com.lemint.tea.community.dto.BoardPostRequest;
import com.lemint.tea.community.dto.BoardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {
  private final BoardService service;

  @GetMapping("/{id}")
  public ResponseEntity<?> getBoardDetail(@PathVariable Long id) {
    BoardResponse response = service.getBoardDetail(id);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/create")
  public ResponseEntity createBoard(@RequestBody BoardPostRequest boardDto
  , @RequestBody List<BoardAttachPostRequest> attachDtoList) {
    Long id = service.createBoard(boardDto, attachDtoList);
    return ResponseEntity.ok(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity updateBoard(@PathVariable Long id, @RequestBody BoardPostRequest dto) {
    service.updateBoard(id, dto);
    return ResponseEntity.ok(id);
  }
}
