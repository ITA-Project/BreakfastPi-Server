package com.ita.domain.service;

import com.tencentcloudapi.tiia.v20190529.models.DetectMisbehaviorResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    boolean upload(MultipartFile file);

    DetectMisbehaviorResponse check(MultipartFile file);
}
