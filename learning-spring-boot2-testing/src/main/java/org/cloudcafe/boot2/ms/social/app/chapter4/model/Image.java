package org.cloudcafe.boot2.ms.social.app.chapter4.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document
public class Image {

    @Id
    private String id;

    private String name;
}
