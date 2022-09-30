package com.example.repository;

import com.example.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Created 30/09/2022 - 17:41
 * @Package com.example.repository
 * @Project file-upload-download-example-1
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, String> {
    Optional<Attachment> findByFileName(String fileName);
}
