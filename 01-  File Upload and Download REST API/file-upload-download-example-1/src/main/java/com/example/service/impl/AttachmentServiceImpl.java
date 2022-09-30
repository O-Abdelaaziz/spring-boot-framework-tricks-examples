package com.example.service.impl;

import com.example.entity.Attachment;
import com.example.exception.FileNotFoundException;
import com.example.exception.FileStorageException;
import com.example.property.FileStorageProperties;
import com.example.repository.AttachmentRepository;
import com.example.service.IAttachmentService;
import com.example.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

/**
 * @Created 30/09/2022 - 17:43
 * @Package com.example.service.impl
 * @Project file-upload-download-example-1
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Service
public class AttachmentServiceImpl implements IAttachmentService {

    private final Path fileStorageLocation;
    private AttachmentRepository attachmentRepository;

    @Autowired
    public AttachmentServiceImpl(AttachmentRepository attachmentRepository, FileStorageProperties fileStorageProperties) {
        this.attachmentRepository = attachmentRepository;
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            attachmentRepository.save(
                    Attachment.builder()
                            .fileName(fileName)
                            .fileType(file.getContentType())
                            .filePath(targetLocation.toString())
                            .build()
            );

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public String storeFileToDatabase(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Attachment attachment = attachmentRepository.save(
                    Attachment.builder()
                            .fileName(file.getOriginalFilename())
                            .fileType(file.getContentType())
                            .data(ImageUtils.compressImage(file.getBytes())).build());
            if (attachment != null) {
                return "File uploaded successfully : " + attachment.getFileName();
            }
            return null;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            //Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Optional<Attachment> attachment = attachmentRepository.findByFileName(fileName);
            Path filePath = Paths.get(attachment.get().getFilePath());
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName, ex);
        }
    }

    @Override
    public Attachment loadFileAsResourceFromDatabase(String fileName) {
        return attachmentRepository.findByFileName(fileName)
                .orElseThrow(() -> new FileNotFoundException("File not found with id " + fileName));
    }
}
