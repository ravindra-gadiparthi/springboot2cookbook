package org.cloudcafe.boot2.ms.social.app;

import org.cloudcafe.boot2.ms.social.app.comments.Comment;
import org.cloudcafe.boot2.ms.social.app.images.Image;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;

@SpringBootApplication
public class LearningSpringBootChapter6MqApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearningSpringBootChapter6MqApplication.class, args);
    }

    @Bean
    CommandLineRunner init(MongoOperations operations) {
        return (args) -> {

            operations.dropCollection(Image.class);
            operations.dropCollection(Comment.class);

            operations.insert(new Image("1",
                    "learning-spring-boot-cover.jpg"));
            operations.insert(new Image("2",
                    "learning-spring-boot-2nd-edition-cover.jpg"));
            operations.insert(new Image("3",
                    "bazinga.png"));

            operations.insert(new Comment("1", "1", "Awesome Boot"));
            operations.insert(new Comment("2", "3", "Awesome Boo2"));
            operations.insert(new Comment("3", "3", "Awesome Boot3"));

            System.out.println(operations.findAll(Image.class));
        };
    }
}
