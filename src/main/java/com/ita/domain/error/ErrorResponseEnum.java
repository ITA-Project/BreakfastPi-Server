package com.ita.domain.error;

import lombok.Getter;

/**
 * @author Dillon Xie
 * @date 12/14/2020
 */
@Getter
public enum ErrorResponseEnum implements ErrorResponse {
    //通用错误类型10001
    PARAMETER_VALIDATION_ERROR(10001, "参数不合法"),
    UNKNOWN_ERROR(10002, "未知错误"),

    //20000开头为用户信息相关错误定义
    USER_NOT_EXIST(20001, "用户不存在"),
    USER_NOT_LOGIN_(20002, "用户还未登录"),
    //30000开头为交易信息相关错误定义
    STOCK_NOT_ENOUGH(30001, "库存不足"),
    CART_IS_EMPTY(30002, "购物车为空"),
    BOX_NOT_ENOUGH(30003, "没有多余的柜子"),
    ORDER_NOT_EXIST(30004, "订单不存在"),
    ORDER_STATUS_INCORRECT(30005, "订单状态不正确"),
    ORDER_NUMBER_INCORRECT(30006, "订单号不存在"),
    ORDER_DATA_INCORRECT(30007, "订单数据不正确"),
    ACCESS_NOT_LOGIN(40001, "认证失败"),
    ACCESS_DENY(40002, "权限不足"),
    ACCESS_LOGIN_FAIL(40003, "登录失败"),
    ;

    private final int errCode;
    private String errMsg;


    ErrorResponseEnum(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public ErrorResponse setErrMsg(String errMsg) {
        this.errMsg=errMsg;
        return this;
    }
}
