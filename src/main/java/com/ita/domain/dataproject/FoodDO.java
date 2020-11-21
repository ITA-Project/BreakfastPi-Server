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
public class FoodDO {
    private Integer id;
    private String categoryId;
    private String name;
    private String imageUrl;
    private String detail;
    private Double price;
    private Integer stock;
    private Integer sales;
    private Integer status; // 1-在售 2-下架（由于某些原因暂时下架） 3-删除(删除后将不再展现给用户)
    @CreatedDate
    private LocalDateTime createTime;
    @LastModifiedDate
    private LocalDateTime updateTime;
}
