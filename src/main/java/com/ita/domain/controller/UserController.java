package com.ita.domain.controller;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.ita.domain.dto.UserAccountDTO;
import com.ita.domain.dto.UserDTO;
import com.ita.domain.entity.User;
import com.ita.domain.enums.UserRole;
import com.ita.domain.error.BusinessException;
import com.ita.domain.error.ErrorResponseEnum;
import com.ita.domain.service.LoginService;
import com.ita.domain.service.impl.UserServiceImpl;
import com.ita.utils.JWTTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserServiceImpl userService;

  @Autowired
  private LoginService loginService;

  @Autowired
  private RedisTemplate<String, String> redisTemplate;


  @GetMapping("login")
  public ResponseEntity<Map<String, String>> login(@RequestParam("code") String code, HttpServletResponse response) throws Exception {
    String openId = loginService.login(code);
    User dbExistedUser = userService.selectByOpenId(openId);
    User user = new User();
    String token = "";
    if (!Objects.isNull(dbExistedUser)) {
      token = JWTTokenUtils.getUserToken(dbExistedUser);
      user = dbExistedUser;
    } else {
      user.setOpenid(openId);
      user.setRole(UserRole.PURCHASER.getRole());
      user.setUsername("wxuser" + openId);
      int i = userService.create(user);
      token = JWTTokenUtils.getUserToken(user);
    }
    if (StringUtils.isEmpty(token)) {
      throw new BusinessException(ErrorResponseEnum.ACCESS_LOGIN_FAIL);
    } else {
      redisTemplate.opsForValue().set(JWT.decode(token).getKeyId(), JSON.toJSONString(user));
    }
    Map<String, String> userInfo = new HashMap<>();
    userInfo.put("token", token);
    userInfo.put("userid", user.getId().toString());
    return ResponseEntity.ok().body(userInfo);
  }

  @PostMapping("normal-login")
  public ResponseEntity<UserDTO> normalLogin(@RequestBody UserAccountDTO account, HttpServletResponse response) {
    return loginService.normalLogin(account.getUsername(), account.getPassword(), response);
  }

}
