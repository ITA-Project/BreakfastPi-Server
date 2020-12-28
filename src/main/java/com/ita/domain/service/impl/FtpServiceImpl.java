package com.ita.domain.service.impl;

import com.ita.domain.config.FtpConfig;
import com.ita.domain.service.FtpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class FtpServiceImpl implements FtpService {

    @Lazy
    private final FTPClient ftpClient;

    private final FtpConfig ftpConfig;

    public FtpServiceImpl(FTPClient ftpClient, FtpConfig ftpConfig) {
        this.ftpClient = ftpClient;
        this.ftpConfig = ftpConfig;
    }

    @Override
    public boolean uploadFile(MultipartFile file) {
        if (ftpClient == null) {
            throw new NullPointerException("Can not connect to FTP server, upload fail!");
        }
        String originName = file.getOriginalFilename();
        try {
            InputStream inputStream = file.getInputStream();
            String remoteDir = ftpConfig.getPath();
            ftpClient.changeWorkingDirectory(remoteDir);
            ftpClient.storeFile(originName, inputStream);
            log.info("Uploaded {} to FTP server", originName);
            ftpClient.logout();
            return true;
        } catch (IOException e) {
            log.error("Failed to upload {} to FTP server", originName);
            return false;
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

}
