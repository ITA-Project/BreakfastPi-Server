package com.ita.domain.service;

import com.ita.domain.dto.SearchHistoryDTO;
import java.util.List;

public interface SearchHistoryService {

  List<SearchHistoryDTO> getUserSearchHistoryDTO(Integer userId);

}
