package com.lemint.tea.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.net.URL;

@Slf4j
@Entity
@Table(name = "TB_ATTACH")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attach extends BaseEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Comment("일련 번호")
  private Long id;

  @Comment("파일 유형")
  private String contentType;

  @Column(nullable = false)
  @Comment("파일 이름")
  private String filename;

  @Column(nullable = false)
  @Comment("파일 경로")
  private String path;

  @Column(nullable = false)
  @Comment("파일 크기")
  private Long size;

  @Comment("대체 문구")
  private String alt;

  @Comment("제목")
  private String title;

  @Transient
  private URL url;

  private Attach(String contentType, String filename, String path, Long size, String alt, String title) {
    log.info("Attach NEW");

    this.contentType = contentType;
    this.filename = filename;
    this.path = path;
    this.size = size;
    this.alt = alt;
    this.title = title;
  }

  public static Attach createEntity(String contentType, String filename, String path, Long size, String alt, String title) {
    log.info("Attach createEntity");

    return new Attach(contentType, filename, path, size, alt, title);
  }

  public void removeEntity() {
    log.info("Attach remove");

    remove();
  }
}
