package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Created 30/09/2022 - 17:44
 * @Package com.example.exception
 * @Project file-upload-download-example-1
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(String message) {
        super(message);
    }

    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
