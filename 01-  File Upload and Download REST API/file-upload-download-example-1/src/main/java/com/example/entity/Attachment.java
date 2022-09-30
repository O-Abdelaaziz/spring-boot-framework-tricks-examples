package com.example.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Created 30/09/2022 - 17:36
 * @Package com.example.entity
 * @Project file-upload-download-example-1
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "attachments")
public class Attachment {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    // to store file path in database
    @Column(name = "file_path")
    private String filePath;

    // to store complete file in data base as byte
    //    @Lob
    //    @Column(name = "data")
    //    private byte[] data;

}
