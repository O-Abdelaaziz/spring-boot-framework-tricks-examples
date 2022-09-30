package com.example;

import com.example.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class FileUploadDownloadExample1Application {

    public static void main(String[] args) {
        SpringApplication.run(FileUploadDownloadExample1Application.class, args);
    }

}
