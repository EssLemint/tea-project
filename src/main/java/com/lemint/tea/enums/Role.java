package com.lemint.tea.enums;

public enum Role {
  ROLE_USER,
  ROLE_MANAGER,
  ROLE_ADMIN,
  ROLE_ANONYMOUS;


  public class SecRoles {
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
    public static final String ADMIN = "ROLE_ADMIN";
    public static final String MANAGER = "ROLE_MANAGER";
    public static final String USER = "ROLE_USER";
  }
}
