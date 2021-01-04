package com.ita.domain.dto;

import com.ita.domain.entity.UserSearchHistory;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchHistoryDTO {
  private int shopId;

  private String searchKey;

  public static SearchHistoryDTO of(UserSearchHistory userSearchHistory) {
    if (Objects.isNull(userSearchHistory)) {
      return SearchHistoryDTO.builder().build();
    }
    return SearchHistoryDTO.builder()
        .shopId(userSearchHistory.getShopId())
        .searchKey(userSearchHistory.getSearchKey())
        .build();
  }
}
