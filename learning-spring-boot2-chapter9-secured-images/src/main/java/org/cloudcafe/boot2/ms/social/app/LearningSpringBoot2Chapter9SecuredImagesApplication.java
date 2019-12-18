package org.cloudcafe.boot2.ms.social.app;

import org.cloudcafe.boot2.ms.social.app.comments.Comment;
import org.cloudcafe.boot2.ms.social.app.images.Image;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;

@SpringCloudApplication
@EnableBinding({Processor.class})
public class LearningSpringBoot2Chapter9SecuredImagesApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearningSpringBoot2Chapter9SecuredImagesApplication.class, args);
    }

    @Bean
    CommandLineRunner loadData(MongoOperations operations) {
        return (args) -> {
            operations.dropCollection(Comment.class);
            operations.dropCollection(Image.class);
            operations.insert(new Image("1",
                    "pic1.jpg","phil"));
            operations.insert(new Image("2",
                    "pic4.jpg","webb"));

            System.out.println(operations.findAll(Image.class));
        };
    }
}
