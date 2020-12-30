package com.ita.domain.service;

import com.ita.domain.dto.UserDTO;
import java.util.Map;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

public interface LoginService {

    Map<String, String> login(String code, String role) throws Exception;

    ResponseEntity<UserDTO> normalLogin(String username, String password, HttpServletResponse response);

}
