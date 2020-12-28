package com.ita.domain.service.impl;

import com.ita.domain.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    @Value(value = "${image.path}")
    private String path;

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
        } catch (IOException e) {
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

}
