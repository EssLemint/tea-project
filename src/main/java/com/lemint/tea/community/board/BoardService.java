package com.lemint.tea.community.board;

import com.lemint.tea.community.attach.AttachRepository;
import com.lemint.tea.community.attachmapping.AttachMappingRepository;
import com.lemint.tea.community.member.MemberRepository;
import com.lemint.tea.community.request.BoardPostRequest;
import com.lemint.tea.community.response.BoardResponse;
import com.lemint.tea.entity.Attach;
import com.lemint.tea.entity.Board;
import com.lemint.tea.entity.BoardAttachMapping;
import com.lemint.tea.entity.Member;
import com.lemint.tea.util.FileUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.lemint.tea.util.TokenUtil.signedId;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

  private final MemberRepository memberRepository;
  private final BoardRepository repository;
  private final AttachRepository attachRepository;
  private final AttachMappingRepository attachMappingRepository;
  private final FileUtil fileUtil;

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
  public Long createBoard(BoardPostRequest dto) throws IOException {
    log.info("BoardService createBoard");
    Long id = signedId.get();

    Member member = memberRepository.findById(1L).orElseThrow(EntityNotFoundException::new);

    /**
     * 현재 board에 attachmapping이 1대N의 연관 관계로 저장 할 시 Mapping도 가지고 저장을 해야함.
     * */

    Board entity = Board.createBoard(dto.getTitle(), dto.getContent()
        , 1L, 1L, member);
    Board board = repository.save(entity);

    if (!Objects.isNull(dto.getFiles()) &&  (dto.getFiles().size() != 0) ){
      //Attach 생성, 저장
      List<Attach> attachList = new ArrayList<>();
      List<MultipartFile> dtoFiles = dto.getFiles();
      for (MultipartFile dtoFile : dtoFiles) {
        String uploadFilePath = fileUtil.upload(dtoFile, "tea-upload");
        Attach attach = Attach.createEntity(
            dtoFile.getOriginalFilename().split("\\.")[1] //확장자
            , dtoFile.getOriginalFilename().split("\\.")[0]//파일원본명
            , uploadFilePath  //업로드 파일 path
            , dtoFile.getSize()); //파일 용량
        attachList.add(attach);
      }
      attachRepository.saveAll(attachList);

      //AttachMapping 저장
      int sort = 0;
      List<BoardAttachMapping> mappingList = new ArrayList<>();
      for (Attach attach : attachList) {
        sort++;
        BoardAttachMapping mapping = BoardAttachMapping.createEntity(board, attach, sort);
        mappingList.add(mapping);
      }
      attachMappingRepository.saveAll(mappingList);
    }

    return board.getId();
  }

  @Transactional
  public Long updateBoard(Long id, BoardPostRequest dto) {
    Board board = repository.findById(id).orElseThrow(EntityNotFoundException::new);
    board.updateBoard(dto.getTitle(), dto.getContent(), 1L);

    return board.getId();
  }

}
