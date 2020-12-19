package com.ita.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Integer id;

    private String username;

    private String password;

    private String openid;

    private String phone;

    private String address;

    private String department;

    private String role;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}