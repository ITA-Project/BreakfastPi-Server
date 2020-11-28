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
public class BoxDO {
    private Integer id;
    private String address;
    private String number;
    private String status; // -1:不可用 0:空箱 1:空箱(已预订) 2:有早餐
    @CreatedDate
    private LocalDateTime createTime;
    @LastModifiedDate
    private LocalDateTime updateTime;
}
