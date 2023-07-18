package com.lemint.tea.common;


/**
 * TEST에서 사용 할 AccessToken 저장용...
 * */
public class ThreadToken {
  public static ThreadLocal<String> threadAccessToken = new ThreadLocal<>();
}
