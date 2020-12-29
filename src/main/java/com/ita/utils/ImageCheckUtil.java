package com.ita.utils;

import com.ita.domain.config.ImageConfig;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.tiia.v20190529.TiiaClient;
import com.tencentcloudapi.tiia.v20190529.models.DetectMisbehaviorRequest;
import com.tencentcloudapi.tiia.v20190529.models.DetectMisbehaviorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ImageCheckUtil {

    @Autowired
    private ImageConfig imageConfig;

    @Value("${aes.secret}")
    private String secret;

    public DetectMisbehaviorResponse check(String imageBase64) {
        try {
            String secretId = AESUtil.decrypt(imageConfig.getSecretId(), secret);
            String secretKey = AESUtil.decrypt(imageConfig.getSecretKey(), secret);
            Credential cred = new Credential(secretId, secretKey);

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("tiia.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            TiiaClient client = new TiiaClient(cred, "ap-guangzhou", clientProfile);

            DetectMisbehaviorRequest req = new DetectMisbehaviorRequest();
            req.setImageBase64(imageBase64);
            return client.DetectMisbehavior(req);
        } catch (TencentCloudSDKException e) {
            log.error("Failed to check image");
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
