package com.ita.domain.service;

import org.springframework.web.multipart.MultipartFile;

public interface FtpService {

    boolean uploadFile(MultipartFile file);

}
