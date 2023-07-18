package com.lemint.tea.community.board;

import com.lemint.tea.community.request.BoardPostRequest;
import com.lemint.tea.community.response.BoardResponse;
import com.lemint.tea.enums.Role;
import com.lemint.tea.enums.Role.SecRoles;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.lemint.tea.enums.Role.SecRoles.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
  private final BoardService service;

  @Secured({ANONYMOUS, USER, MANAGER, ADMIN})
  @GetMapping("/{id}")
  public ResponseEntity<?> getBoardDetail(@PathVariable Long id) {
    BoardResponse response = service.getBoardDetail(id);
    return ResponseEntity.ok(response);
  }

  @Secured({USER, MANAGER, ADMIN})
  @PostMapping(value = "/create")
  public ResponseEntity<?> createBoard(@ModelAttribute @Valid BoardPostRequest dto) throws IOException {
    Long id = service.createBoard(dto);
    return ResponseEntity.ok(id);
  }

  @Secured({USER, MANAGER, ADMIN})
  @PutMapping("/{id}")
  public ResponseEntity<?> updateBoard(@PathVariable Long id, @RequestBody BoardPostRequest dto) throws IOException {
    service.updateBoard(id, dto);
    return ResponseEntity.ok(id);
  }
}
