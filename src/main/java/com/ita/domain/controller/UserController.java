package com.ita.domain.controller;

import com.ita.domain.config.MiniProgramerConfig;
import com.ita.domain.config.MqttConfig;
import com.ita.domain.entity.User;
import com.ita.domain.enums.UserRole;
import com.ita.domain.service.LoginService;
import com.ita.domain.service.UserService;
import com.ita.domain.service.impl.PurchaserIdGenerateServiceImpl;
import com.ita.domain.service.impl.UserServiceImpl;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
      int i = userService.create(user);
      return ResponseEntity.ok().body(user.getId());
    }
  }

}
