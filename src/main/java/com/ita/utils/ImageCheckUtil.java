package com.ita.utils;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.tiia.v20190529.TiiaClient;
import com.tencentcloudapi.tiia.v20190529.models.DetectMisbehaviorRequest;
import com.tencentcloudapi.tiia.v20190529.models.DetectMisbehaviorResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImageCheckUtil {

    public static String check() {
        try {

            Credential cred = new Credential("AKIDHMLZ1Kc04j80B8gW2TfbastiOtoUE1Wu", "LYZMt9EdRo27tPoyHK70c1CH65kCOLsK");

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("tiia.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            TiiaClient client = new TiiaClient(cred, "ap-guangzhou", clientProfile);

            DetectMisbehaviorRequest req = new DetectMisbehaviorRequest();
            req.setImageUrl("https://xiekunlong.cn/resources/5fcf3474e1bc244197189181.jpg");

            DetectMisbehaviorResponse resp = client.DetectMisbehavior(req);
            System.out.println(DetectMisbehaviorResponse.toJsonString(resp));
            return DetectMisbehaviorResponse.toJsonString(resp);
        } catch (TencentCloudSDKException e) {
            log.error("Failed to check image");
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
