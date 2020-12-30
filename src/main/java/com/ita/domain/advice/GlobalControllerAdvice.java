package com.ita.domain.advice;

import static com.ita.common.constant.Constant.FAILED;

import com.ita.domain.error.BusinessException;
import com.ita.domain.error.ErrorResponseEnum;
import com.ita.domain.response.CommonResponseModel;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Dillon Xie
 * @date 12/22/2020
 */
@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommonResponseModel handleException(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        Map<String,Object> responseData=new HashMap<>();

        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException)e;
            setResponseData(responseData, businessException.getErrCode(), businessException.getErrMsg());
        } else if(e instanceof AccessDeniedException) {
            setResponseData(responseData, ErrorResponseEnum.ACCESS_DENY.getErrCode(), ErrorResponseEnum.ACCESS_DENY.getErrMsg());
        }else {
            setResponseData(responseData,ErrorResponseEnum.UNKNOWN_ERROR.getErrCode(), ErrorResponseEnum.UNKNOWN_ERROR.getErrMsg());
        }
        return CommonResponseModel.create(responseData, FAILED);
    }

    private void setResponseData(Map<String, Object> responseData, int code, String msg) {
        responseData.put("errCode", code);
        responseData.put("errMsg",msg);
    }
}
