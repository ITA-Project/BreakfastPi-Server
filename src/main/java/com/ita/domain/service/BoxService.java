package com.ita.domain.service;

import com.ita.domain.entity.Box;

import java.util.List;

public interface BoxService {

    Box selectById(Integer id);

    List<Box> selectAll();

    int create(Box box);

    int delete(Integer id);

    int update(Box box);

    boolean openAssociateOrdersBoxes(List<Integer> orderIds);
}
