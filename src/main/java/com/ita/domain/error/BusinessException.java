package com.ita.domain.error;

import lombok.Getter;

/**
 * @author Dillon Xie
 * @date 12/14/2020
 */
@Getter
public class BusinessException extends Exception implements ErrorResponse {

    private ErrorResponse errorResponse;

    public BusinessException(ErrorResponse errorResponse) {
        super();
        this.errorResponse = errorResponse;
    }

    public BusinessException(ErrorResponse errorResponse, String errorMessage) {
        super();
        this.errorResponse = errorResponse;
        this.errorResponse.setErrMsg(errorMessage); // 重写error信息
    }

    @Override
    public int getErrCode() {
        return this.errorResponse.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.errorResponse.getErrMsg();
    }

    @Override
    public ErrorResponse setErrMsg(String errMsg) {
        this.errorResponse.setErrMsg(errMsg);
        return this;
    }
}
