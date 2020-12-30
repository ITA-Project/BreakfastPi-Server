package com.ita.domain.service.impl;

import com.ita.domain.entity.User;
import com.ita.domain.mapper.UserMapper;
import com.ita.domain.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public int create(User user) {
        return this.userMapper.insert(user);
    }

    @Override
    public User selectByOpenId(String openId) {
        return userMapper.selectByOpenId(openId);
    }

    @Override
    public Optional<User> selectByUsername(String username) {
        return Optional.ofNullable(userMapper.selectByUsername(username));
    }

    @Override
    public User selectById(Integer userId) {
        return Optional.ofNullable(userMapper.selectByPrimaryKey(userId)).orElse(null);
    }
}
