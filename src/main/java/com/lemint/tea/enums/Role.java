package com.lemint.tea.enums;

public enum Role {
  USER,
  MANAGER,
  ADMIN,
  ANONYMOUS;


  public static class SecRoles {
    public static final String ANONYMOUS = "ANONYMOUS";
    public static final String ADMIN = "ADMIN";
    public static final String MANAGER = "MANAGER";
    public static final String USER = "USER";
  }
}
