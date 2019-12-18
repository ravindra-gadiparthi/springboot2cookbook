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
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringCloudApplication
@EnableBinding({Processor.class})
@CrossOrigin(origins = "http://localhost:3000")
public class LearningSpringBoot2Chapter7ImagesApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearningSpringBoot2Chapter7ImagesApplication.class, args);
    }

    @Bean
    CommandLineRunner init(MongoOperations operations) {
        return (args) -> {
            operations.dropCollection(Comment.class);
            operations.dropCollection(Image.class);
            operations.insert(new Image("1",
                    "pic1.jpg"));
            operations.insert(new Image("2",
                    "pic2.jpg"));
            operations.insert(new Image("3",
                    "pic3.png"));

            System.out.println(operations.findAll(Image.class));
        };
    }
}
