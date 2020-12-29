package com.ita.domain.service.impl;

import com.ita.domain.config.MiniProgramerConfig;
import com.ita.domain.dto.UserDTO;
import com.ita.domain.entity.User;
import com.ita.domain.service.LoginService;
import com.ita.domain.service.UserService;
import com.ita.utils.JWTTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.ita.common.constant.Constant.HEADER_AUTHORIZATION;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    private final MiniProgramerConfig miniProgramerConfig;

    private final UserService userServiceImpl;

    public LoginServiceImpl(MiniProgramerConfig miniProgramerConfig, UserService userServiceImpl) {
        this.miniProgramerConfig = miniProgramerConfig;
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public String login(String code) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String appId = miniProgramerConfig.getAppId();
        String appSecret = miniProgramerConfig.getAppSecret();
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + appSecret + "&js_code=" + code + "&grant_type"
                + "=authorization_code";
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse = httpclient.execute(httpGet);
        try {
            HttpEntity entity = httpResponse.getEntity();
            StatusLine statusLine = httpResponse.getStatusLine();
//      int statusCode = statusLine.getStatusCode();
//      EntityUtils.consume(entity);
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
        response.setHeader(HEADER_AUTHORIZATION, JWTTokenUtils.getUserToken(user.get()));
        return ResponseEntity.ok(UserDTO.of(user.get()));
    }
}
