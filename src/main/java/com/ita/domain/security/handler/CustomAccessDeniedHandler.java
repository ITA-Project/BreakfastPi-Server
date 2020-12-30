package com.ita.domain.security.handler;

import static com.ita.common.constant.Constant.FAILED;

import com.alibaba.fastjson.JSON;
import com.ita.domain.error.ErrorResponseEnum;
import com.ita.domain.response.CommonResponseModel;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * 访问接口无权限时的处理端点, 一般是抛出AccessDeniedException异常时会进入
 */
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(
        HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        Map<String,Object> responseData=new HashMap<>();
        responseData.put("errCode", ErrorResponseEnum.ACCESS_DENY.getErrCode());
        responseData.put("errMsg",ErrorResponseEnum.ACCESS_DENY.getErrMsg());
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().print(JSON.toJSON(CommonResponseModel.create(responseData, FAILED)).toString());
    }
}
