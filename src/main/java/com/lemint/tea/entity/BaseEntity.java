package com.lemint.tea.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

  @Comment("사용 여부")
  @ColumnDefault("true")
  protected Boolean useFlag;

  @Comment("삭제 여부")
  @ColumnDefault("false")
  protected Boolean delFlag;


  @CreatedDate
  @Comment("생성 날짜")
  protected LocalDateTime createDate;

  @LastModifiedDate
  @Comment("수정 날짜")
  protected LocalDateTime modifiedDate;

  /**
   * 데이터 논리 삭제
   * @apiNote 데이터 삭제 공통 처리
   * @since 2023-04-12
   * */
  protected void remove() {
    this.useFlag = false;
    this.delFlag = true;
  }

}
