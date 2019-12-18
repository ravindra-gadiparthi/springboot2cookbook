package org.cloudcafe.boot2.ms.social.app;

import org.cloudcafe.boot2.ms.social.app.comments.CommentPublisherBinding;
import org.cloudcafe.boot2.ms.social.app.comments.CommentsSubscriberBinding;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringCloudApplication
@EnableBinding({CommentsSubscriberBinding.class, CommentPublisherBinding.class})
public class LearningSpringBoot2Chapter7CommentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearningSpringBoot2Chapter7CommentsApplication.class, args);
    }
}
