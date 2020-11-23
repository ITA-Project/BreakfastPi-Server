package com.ita.domain.dataobject;

import lombok.Data;

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
}
