package com.ita.domain.security;

import com.alibaba.fastjson.JSON;
import com.ita.domain.constant.HttpParameterConstant;
import com.ita.domain.entity.User;
import com.ita.domain.enums.UserStatusEnum;
import com.ita.domain.error.ErrorResponseEnum;
import com.ita.domain.response.CommonResponseModel;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.ita.common.constant.Constant.FAILED;

public class UserStatusCheckingFilter extends OncePerRequestFilter {

    private final RedisTemplate redisTemplate;

    public UserStatusCheckingFilter(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest reqeust, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String userId = (String) reqeust.getAttribute(HttpParameterConstant.USER_ID);
        if (StringUtils.isEmpty(userId)) {
            chain.doFilter(reqeust, response);
            return;
        }
        String userJson = (String) redisTemplate.opsForValue().get(userId);
        User user = JSON.parseObject(userJson, User.class);
        if (UserStatusEnum.ACTIVE.getCode().equals(user.getStatus())) {
            chain.doFilter(reqeust, response);
        } else {
            wirteResponse(response);
        }
    }

    private void wirteResponse(HttpServletResponse response) throws IOException {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("errCode", ErrorResponseEnum.USER_BANNED.getErrCode());
        responseData.put("errMsg", ErrorResponseEnum.USER_BANNED.getErrMsg());
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(JSON.toJSON(CommonResponseModel.create(responseData, FAILED)).toString());
    }
}
