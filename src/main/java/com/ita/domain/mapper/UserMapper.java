package com.ita.domain.mapper;

import com.ita.domain.entity.User;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    User selectByOpenId(String openId);

    User selectByUsername(String openId);

    List<User> selectAllByStatus(Integer status);
}