package com.ita.domain.service.impl;

import com.ita.domain.service.ImageService;
import com.ita.utils.ImageCheckUtil;
import com.tencentcloudapi.tiia.v20190529.models.DetectMisbehaviorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    @Value(value = "${image.path}")
    private String path;

    @Autowired
    private ImageCheckUtil imageCheckUtil;

    @Override
    public boolean upload(MultipartFile file) {
        File dir = new File(path);
        OutputStream os = null;
        try {
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File tempFile = new File(dir, file.getOriginalFilename());
            os = new FileOutputStream(tempFile);
            os.write(file.getBytes(), 0, file.getBytes().length);
            log.info("Successfully upload {} to {}", tempFile.getName(), tempFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
            return false;
        } finally {
            try {
                os.close();
                file.getInputStream().close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public DetectMisbehaviorResponse check(MultipartFile file) {
        String imageBase64 = "";
        try {
            imageBase64 = Base64.getEncoder().encodeToString(file.getBytes());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
        return imageCheckUtil.check(imageBase64);
    }

}
