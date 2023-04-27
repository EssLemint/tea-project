package com.lemint.tea.community.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardAttachPostRequest {
  /**
   * AttachPostRequest
   * */

  @NotEmpty
  private String contentType;
  @NotEmpty
  private String filename;
  @NotEmpty
  private String path;
  @NotEmpty
  private Long size;
  @NotEmpty
  private String alt;
  @NotEmpty
  private String title;
  private URL url;

}
