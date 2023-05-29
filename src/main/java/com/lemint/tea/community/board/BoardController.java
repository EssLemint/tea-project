package com.lemint.tea.community.board;

import com.lemint.tea.community.request.BoardPostRequest;
import com.lemint.tea.community.response.BoardResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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

  @PostMapping(value = "/create")
  public ResponseEntity createBoard(@ModelAttribute @Valid BoardPostRequest dto) throws IOException {
    Long id = service.createBoard(dto);
    return ResponseEntity.ok(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity updateBoard(@PathVariable Long id, @RequestBody BoardPostRequest dto) {
    service.updateBoard(id, dto);
    return ResponseEntity.ok(id);
  }
}
