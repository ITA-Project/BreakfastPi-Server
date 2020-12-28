package com.ita.domain.config;

import com.ita.utils.AESUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.SocketException;

@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "ftp")
public class FtpConfig {

    private String host;
    private Integer port;
    private String account;
    private String path;

    @Value("${aes.secret}")
    private String secret;

    private String getUsername() {
        String str = AESUtil.decrypt(account, secret);
        assert str != null;
        return str.substring(0, str.indexOf("/"));
    }

    private String getPassword() {
        String str = AESUtil.decrypt(account, secret);
        assert str != null;
        return str.substring(str.indexOf("/") + 1);
    }

    @Bean
    public FTPClient ftpClient() {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setConnectTimeout(1000 * 60 * 2);//设置连接超时时间
        ftpClient.setControlEncoding("utf-8");//设置ftp字符集
        try {
            ftpClient.connect(host, port);
            ftpClient.login(getUsername(), getPassword());
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                ftpClient.disconnect();
                throw new SocketException("Failed to connect FTP server");
            }
            ftpClient.changeWorkingDirectory(path);
            log.info("FTP connected successfully");
            return ftpClient;
        } catch (IOException e) {
            log.error("Failed to connect FTP server");
            log.error(e.getMessage(), e);
            return null;
        }
    }

}
