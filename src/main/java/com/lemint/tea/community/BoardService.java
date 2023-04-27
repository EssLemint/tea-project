package com.lemint.tea.community;

import com.lemint.tea.community.attach.AttachRepository;
import com.lemint.tea.community.attachmapping.AttachMappingRepository;
import com.lemint.tea.community.dto.BoardAttachMappingCreateRequest;
import com.lemint.tea.community.dto.BoardAttachPostRequest;
import com.lemint.tea.community.dto.BoardPostRequest;
import com.lemint.tea.community.dto.BoardResponse;
import com.lemint.tea.entity.Attach;
import com.lemint.tea.entity.Board;
import com.lemint.tea.entity.BoardAttachMapping;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository repository;
  private final AttachRepository attachRepository;
  private final AttachMappingRepository attachMappingRepository;

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
    log.info("BoardService createBoard");

    Board entity = Board.createBoard(dto.getTitle(), dto.getContent()
        , dto.getCreateBy(), dto.getModifiedBy());
    Long id = repository.save(entity).getId(); //Board id

    dto.getAttachMappingList().forEach(item -> {
      Attach attach = attachRepository.findById(item.getAttachId()).orElseThrow(EntityNotFoundException::new);

      BoardAttachMapping mapping = BoardAttachMapping.createEntity(entity, attach, item.getSort());
      attachMappingRepository.save(mapping);
    });

    return id;
  }

  @Transactional
  public Long updateBoard(Long id, BoardPostRequest dto) {
    Board board = repository.findById(id).orElseThrow(EntityNotFoundException::new);
    board.updateBoard(dto.getTitle(), dto.getContent(), dto.getModifiedBy());

    return board.getId();
  }

}
