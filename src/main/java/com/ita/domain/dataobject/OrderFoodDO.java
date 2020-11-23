package com.ita.domain.dataobject;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

/**
 * @author Dillon Xie
 * @date 11/23/2020
 */
@Data
public class OrderFoodDO {
    private Integer id;
    private String foodId;
    private String orderId;
    private Integer quantity;
    @CreatedDate
    private LocalDateTime createTime;
    @LastModifiedDate
    private LocalDateTime updateTime;
}
