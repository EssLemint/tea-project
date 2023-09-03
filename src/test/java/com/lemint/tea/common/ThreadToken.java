package com.lemint.tea.common;


import com.lemint.tea.enums.Role;

/**
 * TEST에서 사용 할 AccessToken 저장용...
 * */
public class ThreadToken {
  public static ThreadLocal<Long> signedId = new ThreadLocal<>();
  public static ThreadLocal<Role> signedRole = new ThreadLocal<>();
  public static ThreadLocal<String> threadAccessToken = new ThreadLocal<>();
}
