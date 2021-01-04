package com.ita.domain.service;

import com.github.pagehelper.PageInfo;
import com.ita.domain.dto.suadmin.UserInfoDTO;
import com.ita.domain.entity.User;
import com.ita.domain.error.BusinessException;

import java.util.Optional;

public interface UserService {

    int create(User user);

    User selectByOpenId(String openId);

    Optional<User> selectByUsername(String username);

    User selectById(Integer userId);

    PageInfo<User> selectByStatus(Integer status, int page, int pageSize);

    User updateUserStatus(UserInfoDTO user) throws BusinessException;

    PageInfo<User> selectAll(int page, int pageSize);

    int updateStatusById(Integer id, Integer status, String statusMessage);
}
