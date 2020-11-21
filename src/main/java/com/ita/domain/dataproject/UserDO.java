package com.ita.domain.dataproject;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

/**
 * @author Dillon Xie
 * @date 11/21/2020
 */
@Data
public class UserDO {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String department;
    private String address;
    @CreatedDate
    private LocalDateTime createTime;
    @LastModifiedDate
    private LocalDateTime updateTime;
}
