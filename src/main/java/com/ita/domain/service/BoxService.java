package com.ita.domain.service;

import com.ita.domain.entity.Box;
import com.ita.domain.mapper.BoxMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoxService {

    private final BoxMapper boxMapper;

    public BoxService(BoxMapper boxMapper) {
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
