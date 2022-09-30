package com.example.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Created 30/09/2022 - 17:47
 * @Package com.example.property
 * @Project file-upload-download-example-1
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
