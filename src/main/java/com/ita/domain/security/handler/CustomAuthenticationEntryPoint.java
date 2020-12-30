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
import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 未登录时的处理端点, 一般是抛出AuthenticationException时会进入
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  @ResponseBody
  public void commence(
      HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
    Map<String,Object> responseData=new HashMap<>();
    responseData.put("errCode",ErrorResponseEnum.ACCESS_NOT_LOGIN.getErrCode());
    responseData.put("errMsg",ErrorResponseEnum.ACCESS_NOT_LOGIN.getErrMsg());
    httpServletResponse.setCharacterEncoding("utf-8");
    httpServletResponse.setContentType("application/json;charset=utf-8");
    httpServletResponse.getWriter().print(JSON.toJSON(CommonResponseModel.create(responseData, FAILED)).toString());
  }
}
