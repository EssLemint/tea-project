package com.lemint.tea.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import static jakarta.persistence.FetchType.LAZY;

@Slf4j
@Entity
@Table(name = "TB_BOARD_MAP_ATTACH")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardAttachMapping {

  @EmbeddedId
  private BoardAttachMappingEmbeddedId id;

  @ColumnDefault("0")
  @Column(name = "sort",nullable = false)
  @Comment("정렬 순서")
  private Integer sort;

  @MapsId("boardId")  //designates a ManyToOne or OneToOne relationship attribute that provides the mapping for an EmbeddedId primary key
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "board_id", nullable = false)
  @Comment("게시글 일련 번호")
  private Board board;


  @MapsId("attachId")
  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "attach_id", nullable = false)
  @Comment("파일 일련 번호")
  private Attach attach;

  private BoardAttachMapping(Integer sort, Board board, Attach attach) {
    log.info("BoardAttachMapping NEW");

    this.id = BoardAttachMappingEmbeddedId.createId(board.getId(), attach.getId());
    this.sort = sort;
    this.board = board;
    this.attach = attach;
  }

  public static BoardAttachMapping createEntity(Board board, Attach attach, Integer sort) {
    log.info("BoardAttachMapping createEntity");

    return new BoardAttachMapping(sort, board, attach);
  }
}
