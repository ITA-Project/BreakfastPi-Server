package com.ita.domain.service.impl;

import com.ita.domain.entity.Box;
import com.ita.domain.mapper.BoxMapper;
import com.ita.domain.service.BoxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class BoxServiceImpl implements BoxService {

    private final BoxMapper boxMapper;

    public BoxServiceImpl(BoxMapper boxMapper) {
        this.boxMapper = boxMapper;
    }

    public Box selectById(Integer id) {
        return boxMapper.selectByPrimaryKey(id);
    }

    public List<Box> selectAll() {
        return boxMapper.selectAll();
    }

    public int create(Box box) {
        return boxMapper.insert(box);
    }

    public int delete(Integer id) {
        return boxMapper.deleteByPrimaryKey(id);
    }

    public int update(Box box) {
        return boxMapper.updateByPrimaryKey(box);
    }
}
