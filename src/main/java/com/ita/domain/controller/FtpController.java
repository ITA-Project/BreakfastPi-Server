package com.ita.domain.controller;

import com.ita.domain.service.FtpService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ftp")
public class FtpController {

    private final FtpService ftpServiceImpl;

    public FtpController(FtpService ftpServiceImpl) {
        this.ftpServiceImpl = ftpServiceImpl;
    }

    @PostMapping
    public boolean upload(@RequestParam MultipartFile file) {
        return ftpServiceImpl.uploadFile(file);
    }
}
