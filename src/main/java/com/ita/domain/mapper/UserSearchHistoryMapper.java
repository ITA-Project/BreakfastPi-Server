package com.ita.domain.mapper;

import com.ita.domain.dto.SearchHistoryDTO;
import com.ita.domain.entity.UserSearchHistory;
import java.util.List;

public interface UserSearchHistoryMapper {

    int insert(UserSearchHistory userSearchHistory);

    List<SearchHistoryDTO> selectUserSearchHistoryByUserId(Integer userId);

}
