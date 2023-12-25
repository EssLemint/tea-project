package com.lemint.tea.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.http.HttpMethod.*;

/**
 * WebMVC 설정.
 *
 * @author FreshR
 * @apiNote Cross-Origin Resource Sharing 설정
 * @since 2022. 12. 23. 오후 3:42:48
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  /**
   * Add cors mappings.
   *
   * @param registry registry
   * @apiNote 설정한 요청에 대해 CORS 허용<br>
   * 현재 모든 요청에 대해 허용하도록 설정되어 있음
   * @author KJE
   * @since 2022. 12. 23. 오후 3:42:48
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods(GET.name(), POST.name(), PUT.name(), DELETE.name(), OPTIONS.name());
  }

  /**
   * Add Resource Mapping
   *
   * @param registry registry
   * @apiNote 정적 자원 접근 제한 해제
   * @author KJE
   * @since 2022. 12. 25
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
  }
}
