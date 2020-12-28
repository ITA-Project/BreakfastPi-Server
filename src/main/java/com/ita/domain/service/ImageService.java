package com.ita.domain.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    boolean upload(MultipartFile file);
}
