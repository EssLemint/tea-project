package com.lemint.tea.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardAttachMappingEmbeddedId implements Serializable {

  private Long boardId;
  private Long attachId;

  private BoardAttachMappingEmbeddedId(Long boardId, Long attachId) {
    log.info("BoardAttachMappingEmbeddedId Constructor");

    this.boardId = boardId;
    this.attachId = attachId;
  }

  public static BoardAttachMappingEmbeddedId createId(Long boardId, Long attachId) {
    log.info("BoardAttachMappingEmbeddedId createId");

    return new BoardAttachMappingEmbeddedId(boardId, attachId);
  }
}
