package org.cloudcafe.boot2.ms.social.app;

import org.cloudcafe.boot2.ms.social.app.chat.ChatServicesStreams;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringCloudApplication
@EnableBinding(ChatServicesStreams.class)
public class LearningSpringBoot2Chapter9SecuredChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearningSpringBoot2Chapter9SecuredChatApplication.class, args);
    }

}
