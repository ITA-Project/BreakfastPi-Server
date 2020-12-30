package com.ita.domain.service.impl;

import com.ita.domain.dto.UploadResponseDTO;
import com.ita.domain.service.ImageService;
import com.ita.utils.ImageCheckUtil;
import com.tencentcloudapi.tiia.v20190529.models.DetectMisbehaviorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Objects;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    @Value(value = "${image.path}")
    private String path;

    @Autowired
    private ImageCheckUtil imageCheckUtil;

    private static final String IMGURL_HOME_URL = "https://imgurl.org/upload/ftp";

    @Override
    public String upload(MultipartFile file) {
        File dir = new File("");
        OutputStream os = null;
        try {
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File tempFile = new File(dir, file.getOriginalFilename());
            os = new FileOutputStream(tempFile);
            os.write(file.getBytes(), 0, file.getBytes().length);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            FileSystemResource fileSystemResource = new FileSystemResource(tempFile);
            MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
            form.add("file", fileSystemResource);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(form, headers);
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setRetryHandler(new DefaultHttpRequestRetryHandler(3, true))
                    .build();
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
            factory.setHttpClient(httpClient);
            factory.setConnectTimeout(1000 * 20);
            factory.setReadTimeout(1000 * 60 * 5);
            RestTemplate restTemplate = new RestTemplate(factory);
            ResponseEntity<UploadResponseDTO> exchange = restTemplate.exchange(IMGURL_HOME_URL, HttpMethod.POST, requestEntity, UploadResponseDTO.class);
            return Objects.requireNonNull(exchange.getBody()).getUrl();
        } catch (IOException e) {
            return null;
        } finally {
            try {
                os.close();
                file.getInputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
