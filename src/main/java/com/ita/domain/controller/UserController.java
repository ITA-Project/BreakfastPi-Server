package com.ita.domain.controller;

import com.github.pagehelper.PageInfo;
import com.ita.domain.dto.UserAccountDTO;
import com.ita.domain.dto.UserDTO;
import com.ita.domain.dto.suadmin.UserInfoDTO;
import com.ita.domain.entity.User;
import com.ita.domain.enums.UserRoleEnum;
import com.ita.domain.error.BusinessException;
import com.ita.domain.service.LoginService;
import com.ita.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @GetMapping("login")
    public ResponseEntity<Map<String, String>> login(@RequestParam("code") String code, HttpServletResponse response) throws Exception {
        return ResponseEntity.ok().body(loginService.login(code, UserRoleEnum.USER.getRole()));
    }

    @GetMapping("rider-login")
    public ResponseEntity<Map<String, String>> riderLogin(@RequestParam("code") String code, HttpServletResponse response) throws Exception {
        return ResponseEntity.ok().body(loginService.login(code, UserRoleEnum.RIDER.getRole()));
    }

    @PostMapping("normal-login")
    public ResponseEntity<UserDTO> normalLogin(@RequestBody UserAccountDTO account, HttpServletResponse response) {
        return loginService.normalLogin(account.getUsername(), account.getPassword(), response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoDTO> getUserInfoById(@PathVariable Integer userId) {
        return ResponseEntity.ok(UserInfoDTO.from(userService.selectById(userId)));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<PageInfo<User>> getUserInfoByStatus(@PathVariable Integer status,
                                                              @RequestParam int page,
                                                              @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(userService.selectByStatus(status, page, pageSize));
    }

    @PutMapping
    public ResponseEntity<UserInfoDTO> updateUserStatus(@RequestBody UserInfoDTO user) throws BusinessException {
        return ResponseEntity.ok(UserInfoDTO.from(userService.updateUserStatus(user)));
    }

    @GetMapping
    public ResponseEntity<PageInfo<User>> getPageableUser(@RequestParam int page, @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(userService.selectAll(page, pageSize));
    }
}
