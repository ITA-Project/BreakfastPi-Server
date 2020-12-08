package com.ita.domain.enums;

public enum UserRole {
  PURCHASER("purchaser"),
  ADMIN("admin"),
  MERCHANT("merchant");


  private String role;

  private UserRole(String role) {this.role = role;}

  public String getRole() {
    return role;
  }
}
