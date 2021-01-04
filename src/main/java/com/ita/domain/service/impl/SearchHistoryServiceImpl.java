package com.ita.domain.service.impl;

import com.ita.domain.dto.SearchHistoryDTO;
import com.ita.domain.mapper.UserSearchHistoryMapper;
import com.ita.domain.service.SearchHistoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchHistoryServiceImpl implements SearchHistoryService {

  @Autowired
  private UserSearchHistoryMapper userSearchHistoryMapper;

  @Override
  public List<SearchHistoryDTO> getUserSearchHistoryDTO(Integer userId) {
    return userSearchHistoryMapper.selectUserSearchHistoryByUserId(userId);
  }
}
