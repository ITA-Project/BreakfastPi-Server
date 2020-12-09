package com.ita.domain.service.impl;

import com.ita.domain.entity.User;
import com.ita.domain.mapper.UserMapper;
import com.ita.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserMapper userMapper;

  @Override
  public int create(User user) {
    return this.userMapper.insert(user);
  }

  @Override
  public User selectByOpenId(String openId) {
    return userMapper.selectByOpenId(openId);
  }
}
