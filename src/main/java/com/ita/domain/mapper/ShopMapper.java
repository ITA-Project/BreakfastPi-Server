package com.ita.domain.mapper;

import com.ita.domain.entity.Shop;

import java.util.List;

public interface ShopMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shop record);

    Shop selectByPrimaryKey(Integer id);

    List<Shop> selectAll();

    int updateByPrimaryKey(Shop record);
}