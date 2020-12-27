package com.ita.domain.mapper;

import com.ita.domain.entity.PayInfo;

import java.util.List;

public interface PayInfoMapper {

    int insert(PayInfo record);
    List<PayInfo> selectAll();
}
