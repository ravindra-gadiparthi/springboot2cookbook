package org.cloudcafe.boot2.ms.social.app.comments;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CommentsSubscriberBinding {

    String INPUT = "learning-spring-boot";

    @Input(INPUT)
    SubscribableChannel subscriberChannel();
}
