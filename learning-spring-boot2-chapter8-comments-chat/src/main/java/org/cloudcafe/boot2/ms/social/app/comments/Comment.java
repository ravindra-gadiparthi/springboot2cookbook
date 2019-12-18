package org.cloudcafe.boot2.ms.social.app.comments;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    private String id;
    private String imageId;
    private String comment;
}
