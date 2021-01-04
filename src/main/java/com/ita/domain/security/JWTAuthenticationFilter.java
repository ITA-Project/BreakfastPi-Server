package com.ita.domain.security;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.ita.domain.constant.HttpParameterConstant;
import com.ita.domain.entity.User;
import com.ita.utils.JWTTokenUtils;
import com.ita.utils.UsernamePasswordAuthenticationTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final RedisTemplate redisTemplate;

    public JWTAuthenticationFilter(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if (!Objects.isNull(authentication)) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = (User) authentication.getPrincipal();
            request.setAttribute("userid", user.getId());
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String bearerTokenString = request.getHeader(JWTTokenUtils.AUTHORIZATION);
        String tokenString = bearerTokenString;
        if (!StringUtils.isEmpty(bearerTokenString) && bearerTokenString.contains(JWTTokenUtils.BEARER)) {
            tokenString = bearerTokenString.replace(JWTTokenUtils.BEARER, "").trim();
        }
        if (StringUtils.isEmpty(tokenString)) {
            return null;
        }
        String keyId = null;
        try {
            keyId = JWT.decode(tokenString).getKeyId();
        } catch (JWTDecodeException e) {
            logger.error("Failed to decode token which is illegal");
            logger.error(e.getMessage(), e);
            return null;
        }
        String redisTokenStr = (String) redisTemplate.opsForValue().get(keyId);
        if (Objects.isNull(redisTokenStr)) {
            return null;
        }
        User redisUser = JSONObject.parseObject(redisTokenStr, User.class);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                UsernamePasswordAuthenticationTokenUtils.generateUsernamePasswordAuthenticationToken(redisUser, redisUser.getRole());
        if (!Objects.isNull(usernamePasswordAuthenticationToken)) {
            User user = (User) usernamePasswordAuthenticationToken.getPrincipal();
            request.setAttribute(HttpParameterConstant.USER_ID, user.getId());
            return usernamePasswordAuthenticationToken;
        } else {
            return null;
        }
    }

}
