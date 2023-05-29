package com.lemint.tea.community.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
public class BoardPostRequest {

  @NotEmpty
  private String title;
  private String content;

  /**
   * AttachPostRequest
   * */
  private String ext; //파일 확장자
  private String filename;  //파일 이름
  private Long size;  //파일 사이즈
  private List<MultipartFile> files; //파일
}
