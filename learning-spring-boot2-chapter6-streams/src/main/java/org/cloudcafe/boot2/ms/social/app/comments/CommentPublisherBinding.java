package org.cloudcafe.boot2.ms.social.app.comments;


import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CommentPublisherBinding {

    @Output("learning-spring-boot")
    MessageChannel CommentPublishChannel();
}
