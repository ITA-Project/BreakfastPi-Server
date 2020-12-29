package com.ita.domain.service;

import com.ita.domain.dto.UserDTO;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

public interface LoginService {

    String login(String code) throws Exception;

    ResponseEntity<UserDTO> normalLogin(String username, String password, HttpServletResponse response);

}
