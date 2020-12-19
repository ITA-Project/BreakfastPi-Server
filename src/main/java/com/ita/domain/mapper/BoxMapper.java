package com.ita.domain.mapper;

import com.ita.domain.entity.Box;

import java.util.List;

public interface BoxMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Box record);

    Box selectByPrimaryKey(Integer id);

    List<Box> selectAll();

    int updateByPrimaryKey(Box record);

    List<Box> selectByStatus(Integer status);
}