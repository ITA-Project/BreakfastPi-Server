package com.ita.domain.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchHistory {
  private int id;

  private int userId;

  private int shopId;

  private String searchKey;

  private LocalDateTime createTime;

  private LocalDateTime updateTime;
}
