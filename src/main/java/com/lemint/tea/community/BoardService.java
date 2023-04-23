package com.lemint.tea.community;

import com.lemint.tea.community.dto.BoardPostRequest;
import com.lemint.tea.community.dto.BoardResponse;
import com.lemint.tea.entity.Board;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository repository;

  public BoardResponse getBoardDetail(Long id) {
    Board board = repository.findById(id).orElseThrow(EntityNotFoundException::new);
    board.countViews();

    return BoardResponse.builder()
        .title(board.getTitle())
        .content(board.getContent())
        .createBy(board.getCreateBy())
        .views(board.getViews())
        .build();
  }

  @Transactional
  public Long createBoard(BoardPostRequest dto) {
    Board board = Board.createBoard(
        dto.getTitle()
        , dto.getContent()
        , dto.getCreateBy()
        , dto.getCreateBy()
    );
    Long id = repository.save(board).getId();

    return id;
  }

  @Transactional
  public Long updateBoard(Long id, BoardPostRequest dto) {
    Board board = repository.findById(id).orElseThrow(EntityNotFoundException::new);
    board.updateBoard(dto.getTitle(), dto.getContent(), dto.getModifiedBy());

    return board.getId();
  }

}
