package com.ita.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    private String statusMessage;

    private Integer status;
}