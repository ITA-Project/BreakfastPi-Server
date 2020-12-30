package com.ita.domain.dto;

import com.ita.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;

    private String username;

    private String openid;

    private String phone;

    private String address;

    private String statusMessage;

    private Integer status;

    private String department;

    private String role;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public static UserDTO of(User user) {
        UserDTO userDTO = UserDTO.builder().build();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }
}
