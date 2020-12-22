package com.ita.domain.response;

import lombok.Builder;
import lombok.Data;

import static com.ita.common.constant.Constant.SUCCESS;

/**
 * @author Dillon Xie
 * @date 12/22/2020
 */
@Data
@Builder
public class CommonResponseModel implements ResponseModel {
    private String status; // 两种状态  success or fail 方便前台统一处理
    private Object data;

    public static CommonResponseModel create(Object object) {
        return CommonResponseModel.create(object, SUCCESS);
    }

    public static CommonResponseModel create(Object object, String status) {
        return CommonResponseModel.builder()
                .status(status)
                .data(object)
                .build();
    }
}
