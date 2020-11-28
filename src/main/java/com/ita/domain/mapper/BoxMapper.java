package com.ita.domain.mapper;

import com.ita.domain.dataobject.BoxDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoxMapper {

    int insert(@Param("box") BoxDO box);

    int delete(Integer id);

    int update(@Param("box") BoxDO box);

    BoxDO selectById(Integer id);

    List<BoxDO> selectAll();
}
