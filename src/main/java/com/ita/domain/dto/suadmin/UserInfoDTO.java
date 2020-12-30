package com.ita.domain.dto.suadmin;

import com.ita.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {
  private String id;
  private String username;
  private String status;
  private String statusMessage;

  public static UserInfoDTO from(User user) {
    return UserInfoDTO.builder()
        .id(String.valueOf(user.getId()))
        .username(user.getUsername())
        .status(String.valueOf(user.getStatus()))
        .statusMessage(user.getStatusMessage())
        .build();
  }
}
