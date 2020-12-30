package com.ita.domain.enums;

public enum UserRoleEnum {
  USER("user"),
  ADMIN("admin"),
  RIDER("rider"),
  MERCHANT("merchant");


  private String role;

  UserRoleEnum(String role) {this.role = role;}

  public String getRole() {
    return role;
  }
}
