package com.ita.domain.controller;

import com.ita.domain.entity.User;
import com.ita.domain.enums.UserRole;
import com.ita.domain.service.LoginService;
import com.ita.domain.service.impl.PurchaserIdGenerateServiceImpl;
import com.ita.domain.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserServiceImpl userService;

  @Autowired
  private LoginService loginService;

  @Autowired
  private PurchaserIdGenerateServiceImpl purchaserIdGenerateService;

  @GetMapping("login")
  public ResponseEntity<Integer> login(@RequestParam("code") String code , HttpServletResponse response) throws Exception {
    String openId = loginService.login(code);
//    User user = new User();
//    user.setOpenid(openId);
//    user.setId(purchaserIdGenerateService.generateId());
//    user.setRole(UserRole.PURCHASER.getRole());
//    return ResponseEntity.ok().body(user.getId());
    User dbExistedUser = userService.selectByOpenId(openId);
    if(!Objects.isNull(dbExistedUser)) {
      return ResponseEntity.ok().body(dbExistedUser.getId());
    } else {
      User user = new User();
      user.setOpenid(openId);
      user.setId(purchaserIdGenerateService.generateId());
      user.setRole(UserRole.PURCHASER.getRole());
      user.setUsername(UUID.randomUUID().toString());
      int i = userService.create(user);
      return ResponseEntity.ok().body(user.getId());
    }
  }

}
