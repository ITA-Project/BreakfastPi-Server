package com.ita.domain.service;

import com.ita.domain.entity.User;

public interface UserService {

  int create (User user) ;

  User selectByOpenId(String openId);

}
