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
public class CategoryDO {
    private Integer id;
    private String name;
    private Integer status; // 1-正常 2 已经废弃
    private Integer sortOrder; //排序编号，同类展示顺序,数值相等则自然排序
    @CreatedDate
    private LocalDateTime createTime;
    @LastModifiedDate
    private LocalDateTime updateTime;
}
