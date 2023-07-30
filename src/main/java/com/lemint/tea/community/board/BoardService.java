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

  /**
   * Board Detail  상세 게시글
   * @param  id :  게시글 번호
   * @return 게시글 response
   * @author lemint
   * @since  2023-06-06
   * */
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

  /**
   *  Board Create 게시글 생성
   *
   * @param dto
   * @return board id
   * @author lemint
   * @since  2023-06-06
   * */
  @Transactional
  public Long createBoard(BoardPostRequest dto) throws IOException {
    log.info("BoardService createBoard");
    Long id = signedId.get();

    //id > 1L로 임시 실행
    Member member = memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);

    Board entity = Board.createBoard(dto.getTitle(), dto.getContent(), id, id, member);
    Board board = repository.save(entity);

    //파일 저장
    if (!Objects.isNull(dto.getFiles()) &&  !dto.getFiles().isEmpty()){
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

  /**
   * @apiNote update board
   * @param id, dto
   * @return board id
   * @author lemint
   * @since 2023-07-30
   * */
  @Transactional
  public Long updateBoard(Long id, BoardPostRequest dto) throws IOException {
    Board board = repository.findById(id).orElseThrow(EntityNotFoundException::new);
    //TODO
    //파일 저장 >> 현재 보드 파일 오리지널 파일이름 확인 중복, 들어오는 sourceFile이 없다면
    //내용만 수정, 추가적으로 변경된 파일만 수정을 할것인가.. 아니면 기존 파일들 다 삭제 처리하고 새로 등록할것인가
    // s3접근, DB 자주 접근 중 뭐로 잡아야하는지 아니면 둘다...?
    if (!Objects.isNull(dto.getFiles()) || !checkFileName(id, dto)) {

    }

    board.updateBoard(dto.getTitle(), dto.getContent(), id);  //게시판 제목, 내용, 수정자
    return board.getId();
  }

  /**
   * @apiNote check change status of file attached to board
   * @param id, dto
   * @return true : no changes, false : change
   * @author lemint
   * @since 2023-07-30
   * */
  private boolean checkFileName(Long id, BoardPostRequest dto) {
    List<String> fileNames = findAttachFileNamesByBoardId(id);  //기존 파일
    List<MultipartFile> multipartFiles = dto.getFiles();  //들어온 파일
    boolean flag = false;

    for (String name : fileNames) {
      for (MultipartFile file : multipartFiles) {
        if (name.equalsIgnoreCase(file.getName())) {
          flag = true;
        }
      }
    }
    return flag;
  }

  /**
   * @apiNote get file names by board id
   * @param id
   * @return List of file names
   * @author lemint
   * @since 2023-07-30
   * */
  private List<String> findAttachFileNamesByBoardId(Long id) {
    //board id > attach mapping > mapping으로 attach id 찾기
    List<BoardAttachMapping> mappingIds = attachMappingRepository.findAttachMappingByBoardId(id);

    List<String> fileNames = new ArrayList<>();
    for (BoardAttachMapping mappings : mappingIds) {
      Attach attach = attachRepository.findById(mappings.getAttach().getId()).orElseThrow(EntityNotFoundException::new);
      String filename = attach.getFilename();
      fileNames.add(filename);
    }
    return fileNames;
  }

}
