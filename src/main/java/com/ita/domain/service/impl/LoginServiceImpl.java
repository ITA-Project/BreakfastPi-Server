package com.ita.domain.service.impl;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.ita.domain.config.RiderMiniProgramerConfig;
import com.ita.domain.config.UserMiniProgramerConfig;
import com.ita.domain.dto.UserDTO;
import com.ita.domain.entity.User;
import com.ita.domain.enums.UserRoleEnum;
import com.ita.domain.enums.UserStatusEnum;
import com.ita.domain.error.BusinessException;
import com.ita.domain.error.ErrorResponseEnum;
import com.ita.domain.service.LoginService;
import com.ita.domain.service.UserService;
import com.ita.utils.JWTTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.ita.common.constant.Constant.HEADER_AUTHORIZATION;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    private final UserMiniProgramerConfig userMiniProgramerConfig;

    private final UserService userServiceImpl;

    private final RedisTemplate<String, String> redisTemplate;

    private final RiderMiniProgramerConfig riderMiniProgramerConfig;

    private final String getOpenIdURL = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";



    public LoginServiceImpl(UserMiniProgramerConfig userMiniProgramerConfig, UserService userServiceImpl,
        RedisTemplate<String, String> redisTemplate, RiderMiniProgramerConfig riderMiniProgramerConfig) {
        this.userMiniProgramerConfig = userMiniProgramerConfig;
        this.userServiceImpl = userServiceImpl;
        this.redisTemplate = redisTemplate;
        this.riderMiniProgramerConfig = riderMiniProgramerConfig;
    }

    @Override
    public Map<String, String> login(String code, String role) throws Exception {
        String openId = this.getOpenId(code, role);
        User dbExistedUser = userServiceImpl.selectByOpenId(openId);
        User user;
        String token;
        if (!Objects.isNull(dbExistedUser)) {
            user = dbExistedUser;
            token = JWTTokenUtils.getUserToken(dbExistedUser);
        } else {
            user = User.builder()
                    .openid(openId)
                    .role(role)
                    .username("wxuser" + openId)
                    .status(UserStatusEnum.ACTIVE.getCode())
                    .statusMessage(UserStatusEnum.ACTIVE.getValue())
                    .build();
            userServiceImpl.create(user);
            token = JWTTokenUtils.getUserToken(user);
        }
        if (StringUtils.isEmpty(token)) {
            throw new BusinessException(ErrorResponseEnum.ACCESS_LOGIN_FAIL);
        } else {
            try {
                redisTemplate.opsForValue().set(JWT.decode(token).getKeyId(), JSON.toJSONString(user));
            } catch (RedisConnectionFailureException e) {
                throw new BusinessException(ErrorResponseEnum.REDIS_CONNECT_FAIL);
            }
        }
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("token", token);
        userInfo.put("userId", user.getId().toString());
        return userInfo;
    }

    private String getOpenId(String code, String role) throws Exception {
        String appSecret = "";
        String appId = "";
        if(role.equals(UserRoleEnum.USER.getRole())) {
            appId = userMiniProgramerConfig.getAppId();
            appSecret = userMiniProgramerConfig.getAppSecret();
        } else if(role.equals(UserRoleEnum.RIDER.getRole())) {
            appId = riderMiniProgramerConfig.getAppId();
            appSecret = riderMiniProgramerConfig.getAppSecret();
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = String.format(getOpenIdURL, appId, appSecret, code);
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        try {
            HttpEntity entity = httpResponse.getEntity();
            if(httpResponse.getStatusLine().getStatusCode() != HttpStatus.OK.value()) {
                throw new BusinessException(ErrorResponseEnum.ACCESS_LOGIN_FAIL);
            }
            String jsonEntity = EntityUtils.toString(entity);
            JSONObject jsonObject = new JSONObject(jsonEntity);
            String openID = jsonObject.getString("openid");
            return openID;
        } finally {
            httpResponse.close();
        }
    }

    @Override
    public ResponseEntity<UserDTO> normalLogin(String username, String password, HttpServletResponse response) {
        Optional<User> user = userServiceImpl.selectByUsername(username)
                .filter(u -> password.equals(u.getPassword()));
        if (!user.isPresent()) {
            return ResponseEntity.ok(UserDTO.builder().build());
        }
        String token = JWTTokenUtils.getUserToken(user.get());
        response.setHeader(HEADER_AUTHORIZATION, token);
        redisTemplate.opsForValue().set(JWT.decode(token).getKeyId(), JSON.toJSONString(user.get()));
        return ResponseEntity.ok(UserDTO.of(user.get()));
    }
}
