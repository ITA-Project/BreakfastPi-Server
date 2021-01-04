package com.ita.domain.controller;

import com.ita.domain.dto.SearchHistoryDTO;
import com.ita.domain.entity.UserSearchHistory;
import com.ita.domain.service.impl.SearchHistoryServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search-history")
public class SearchHistoryController {

  @Autowired
  private SearchHistoryServiceImpl searchHistoryServiceImpl;

  @GetMapping("/{userId}")
  public ResponseEntity<List<SearchHistoryDTO>> getUserSearchHistory(@PathVariable Integer userId){
    return ResponseEntity.ok(searchHistoryServiceImpl.getUserSearchHistoryDTO(userId));
  }

}
