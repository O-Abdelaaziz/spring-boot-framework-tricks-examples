package com.example.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Created 30/09/2022 - 17:57
 * @Package com.example.payload
 * @Project file-upload-download-example-1
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UploadFileResponse {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
}
