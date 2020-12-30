package com.ita.domain.service;

import com.ita.domain.dto.suadmin.UserInfoDTO;
import com.ita.domain.entity.User;

import java.util.Optional;

public interface UserService {

    int create(User user);

    User selectByOpenId(String openId);

    Optional<User> selectByUsername(String username);

    User selectById(Integer userId);

    User updateUserStatus(UserInfoDTO user);

}
