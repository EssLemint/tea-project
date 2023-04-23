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

import java.util.List;

@Slf4j
@Entity
@Table(name = "TB_COMMUNITY_BOARD")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Comment("일련 번호")
  private Long id;

  @Comment("제목")
  @Column(nullable = false, length = 100)
  private String title;

  @Lob
  @Comment("본문")
  @Column(nullable = false)
  private String content;

  @Comment("작성자")
  private Long createBy;

  @Comment("수정자")
  private Long modifiedBy;

  @Comment("조회수")
  @ColumnDefault("0")
  private Integer views;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "board")
  private List<BoardAttachMapping> attachList;

  private Board(String title, String content, Long createBy, Long modifiedBy) {
    this.title = title;
    this.content = content;
    this.createBy = createBy;
    this.modifiedBy = modifiedBy;
  }

  public static Board createBoard(String title, String content, Long createBy, Long modifiedBy) {
    return new Board(title, content, createBy, modifiedBy);
  }

  public void updateBoard(String title, String content, Long modifiedBy) {
    this.title = title;
    this.content = content;
    this.modifiedBy = modifiedBy;
  }

  public void countViews() {
    this.views++;
  }

  public void removeBoard() {
    remove();
  }
}
