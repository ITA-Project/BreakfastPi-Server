package com.ita.domain.advice;

import com.ita.domain.error.BusinessException;
import com.ita.domain.error.ErrorResponseEnum;
import com.ita.domain.response.CommonResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.ita.common.constant.Constant.FAILED;

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
        Map<String,Object> responseData=new HashMap<>();

        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException)e;
            responseData.put("errCode",businessException.getErrCode());
            responseData.put("errMsg",businessException.getErrMsg());
        } else {
            responseData.put("errCode", ErrorResponseEnum.UNKNOWN_ERROR.getErrCode());
            responseData.put("errMsg",ErrorResponseEnum.UNKNOWN_ERROR.getErrMsg());
        }
        return CommonResponseModel.create(responseData, FAILED);
    }
}
