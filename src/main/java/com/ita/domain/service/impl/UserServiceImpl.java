package com.ita.domain.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ita.domain.dto.UserDTO;
import com.ita.domain.dto.suadmin.UserInfoDTO;
import com.ita.domain.entity.User;
import com.ita.domain.error.BusinessException;
import com.ita.domain.mapper.UserMapper;
import com.ita.domain.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ita.domain.error.ErrorResponseEnum.USER_NOT_EXIST;

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

    @Override
    public PageInfo<UserInfoDTO> selectByStatus(Integer status, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return new PageInfo<>(userMapper.selectAllByStatus(status).stream().map(UserInfoDTO::from).collect(Collectors.toList()));
    }

    @Override
    public User updateUserStatus(UserInfoDTO user) throws BusinessException {
        User originalUser = Optional.ofNullable(userMapper.selectByPrimaryKey(Integer.valueOf(user.getId()))).orElse(null);
        if (Objects.isNull(originalUser)) {
            throw new BusinessException(USER_NOT_EXIST);
        }
        originalUser.setStatus(Integer.valueOf(user.getStatus()));
        originalUser.setStatusMessage(user.getStatusMessage());
        userMapper.updateByPrimaryKey(originalUser);
        return userMapper.selectByPrimaryKey(Integer.valueOf(user.getId()));
    }

    @Override
    public PageInfo<UserDTO> selectAll(int page, int pageSize) {
        PageHelper.startPage(page, pageSize, true);
        List<UserDTO> userDTOS = userMapper.selectAll().stream().map(UserDTO::of).collect(Collectors.toList());
        return new PageInfo<>(userDTOS, pageSize);
    }

    @Override
    public int updateStatusById(Integer id, Integer status, String statusMessage) {
        return userMapper.updateStatusById(id, status, statusMessage);
    }
}
