package com.lemint.tea.entity;

import com.lemint.tea.enums.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Slf4j
@Entity
@Table(name = "TB_MEMBER")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @Comment("일련 번호")
  private Long id;

  @Comment("사용자 Id")
  @Column(name = "user_id", nullable = false, unique = true)
  private String userId;

  @Comment("비밀번호")
  @Column(name = "password", nullable = false)
  private String password;

  @Comment("이름")
  @Column(name = "user_name", nullable = false, length = 20)
  private String name;

  @Enumerated(EnumType.STRING)
  @Comment("권한")
  private Role role;



  private Member(String userId, String password, String name, Role role) {
    this.userId = userId;
    this.password = password;
    this.name = name;
    this.role = role;
  }

  public static Member createMember(String userId, String password, String name, Role role) {
    return new Member(userId, password, name, role);
  }

  public void updateMember(String password, String name) {
    this.password = password;
    this.name = name;
  }

  public void updateTempPassword(String password) {
    this.password = password;
  }
}
