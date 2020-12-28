package com.ita.domain.service.impl;

import com.ita.domain.service.FtpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class FtpServiceImpl implements FtpService {

    @Autowired(required = false)
    private FTPClient ftpClient;

    @Override
    public boolean uploadFile(MultipartFile file) {
        if (ftpClient == null) {
            log.error("Disconnect from FTP server, upload {} fail!", file.getOriginalFilename());
            throw new NullPointerException("Disconnect from FTP server, upload fail!");
        }
        String originName = file.getOriginalFilename();
        try {
            InputStream inputStream = file.getInputStream();
            ftpClient.storeFile(originName, inputStream);
            log.info("Uploaded {} to FTP server", originName);
            return true;
        } catch (IOException e) {
            log.error("Failed to upload {} to FTP server", originName);
            log.error(e.getMessage(), e);
            return false;
        }
    }

}
