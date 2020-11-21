package com.ita.domain.dataproject;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Dillon Xie
 * @date 11/21/2020
 */
@Data
public class ShopDO {
    private Integer id;
    private String shopName;
    private String phone;
    private String password;
    private String email;
    private LocalDateTime addTime;
}
