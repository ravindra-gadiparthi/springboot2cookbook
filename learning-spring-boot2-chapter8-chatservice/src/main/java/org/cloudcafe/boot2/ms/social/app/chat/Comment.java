package org.cloudcafe.boot2.ms.social.app.chat;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    private String id;
    private String imageId;
    private String comment;
}
