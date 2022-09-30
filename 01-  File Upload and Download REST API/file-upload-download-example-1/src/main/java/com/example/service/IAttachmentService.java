package com.example.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Created 30/09/2022 - 17:42
 * @Package com.example.service
 * @Project file-upload-download-example-1
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
public interface IAttachmentService {

    public String storeFile(MultipartFile file);

    public Resource loadFileAsResource(String fileName);
}
