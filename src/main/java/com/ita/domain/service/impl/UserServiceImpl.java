package com.ita.domain.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ita.domain.dto.suadmin.UserInfoDTO;
import com.ita.domain.entity.User;
import com.ita.domain.error.BusinessException;
import com.ita.domain.mapper.UserMapper;
import com.ita.domain.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.ita.domain.error.ErrorResponseEnum.USER_NOT_EXIST;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final RedisTemplate redisTemplate;

    public UserServiceImpl(UserMapper userMapper, RedisTemplate redisTemplate) {
        this.userMapper = userMapper;
        this.redisTemplate = redisTemplate;
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

    @Override
    public PageInfo<User> selectByStatus(Integer status, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return new PageInfo<>(userMapper.selectAllByStatus(status));
    }

    @Override
    public User updateUserStatus(UserInfoDTO user) throws BusinessException {
        User originalUser = Optional.ofNullable(userMapper.selectByPrimaryKey(Integer.valueOf(user.getId()))).orElse(null);
        if (Objects.isNull(originalUser)) {
            throw new BusinessException(USER_NOT_EXIST);
        }
        originalUser.setStatus(Integer.valueOf(user.getStatus()));
        originalUser.setStatusMessage(user.getStatusMessage());
        redisTemplate.opsForValue().set(String.valueOf(originalUser.getId()), JSON.toJSONString(originalUser), 1, TimeUnit.DAYS);
        userMapper.updateByPrimaryKey(originalUser);
        return userMapper.selectByPrimaryKey(Integer.valueOf(user.getId()));
    }

    @Override
    public PageInfo<User> selectAll(int page, int pageSize) {
        PageHelper.startPage(page, pageSize, true);
        return new PageInfo<>(userMapper.selectAll());
    }

    @Override
    public int updateStatusById(Integer id, Integer status, String statusMessage) {
        return userMapper.updateStatusById(id, status, statusMessage);
    }
}
