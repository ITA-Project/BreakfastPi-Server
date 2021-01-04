package com.ita.utils;

import com.alibaba.fastjson.JSON;
import com.ita.domain.constant.HttpConstant;
import com.ita.domain.error.BusinessException;
import com.ita.domain.error.ErrorResponseEnum;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

@Slf4j
public class WXServiceUtil {

  private final static String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

  private final static String POST_SEND_SUBSCRIBE_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=%s";

  public static String getWXMiniProgramAccessToken(String appId, String appSecret) throws Exception {
    CloseableHttpClient httpClient = HttpClients.createDefault();
    String url = String.format(GET_ACCESS_TOKEN_URL, appId, appSecret);
    HttpGet httpGet = new HttpGet(url);
    CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
    try {
      HttpEntity entity = httpResponse.getEntity();
      if(httpResponse.getStatusLine().getStatusCode() != HttpStatus.OK.value()) {
        throw new BusinessException(ErrorResponseEnum.WX_GET_ACCESS_TOKEN_FAIL);
      }
      String jsonEntity = EntityUtils.toString(entity);
      JSONObject jsonObject = new JSONObject(jsonEntity);
      String accessToken = jsonObject.getString("access_token");
      return accessToken;
    } finally {
      httpResponse.close();
    }
  }

  public static void sendWXMiniProgramSubscribeMsg(String accessToken, String openId, Map<String, String> data, String templateId, String pageURL,
      String miniProgramState) throws Exception {
    CloseableHttpClient httpClient = HttpClients.createDefault();
    String url = String.format(POST_SEND_SUBSCRIBE_MESSAGE_URL, accessToken);
    HttpPost httpPost = new HttpPost(url);
    HashMap<String, String> postBodyMap = new HashMap<>();
    postBodyMap.put("lang", "zh_CN");
    postBodyMap.put("touser", openId);
    postBodyMap.put("data", JSON.toJSONString(data));
    postBodyMap.put("page", pageURL);
    postBodyMap.put("template_id", templateId);
    postBodyMap.put("miniprogram_state", miniProgramState);
    StringEntity stringEntity = new StringEntity(JSON.toJSONString(postBodyMap),"UTF-8");
    stringEntity.setContentEncoding(HttpConstant.ENCODE_UTF_8);
    stringEntity.setContentType(HttpConstant.ACCEPT_APPLICATION_JSON);
    httpPost.setEntity(stringEntity);
    CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
    try {
      HttpEntity resEntity = httpResponse.getEntity();
      String responseData = EntityUtils.toString(resEntity);
      log.info(responseData);
    } finally {
      httpResponse.close();
    }
  }

}
