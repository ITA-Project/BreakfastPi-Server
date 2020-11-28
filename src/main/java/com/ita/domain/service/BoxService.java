package com.ita.domain.service;

import com.ita.domain.dataobject.BoxDO;
import com.ita.domain.mapper.BoxMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoxService {

    private final BoxMapper boxMapper;

    public BoxService(BoxMapper boxMapper) {
        this.boxMapper = boxMapper;
    }

    public BoxDO selectById(Integer id) {
        return boxMapper.selectById(id);
    }

    public List<BoxDO> selectAll() {
        return boxMapper.selectAll();
    }

    public int create(BoxDO box) {
        return boxMapper.insert(box);
    }

    public int delete(Integer id) {
        return boxMapper.delete(id);
    }

    public int update(BoxDO box) {
        return boxMapper.update(box);
    }
}
