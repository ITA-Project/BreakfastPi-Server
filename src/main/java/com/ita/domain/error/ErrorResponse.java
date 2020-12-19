package com.ita.domain.error;

/**
 * @author Dillon Xie
 * @date 12/14/2020
 */
public interface ErrorResponse {
    int getErrCode();
    String getErrMsg();
    ErrorResponse setErrMsg(String errMsg);
}
