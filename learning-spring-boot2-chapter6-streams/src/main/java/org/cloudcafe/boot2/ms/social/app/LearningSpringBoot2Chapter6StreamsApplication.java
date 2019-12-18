package org.cloudcafe.boot2.ms.social.app;

import org.cloudcafe.boot2.ms.social.app.comments.CommentPublisherBinding;
import org.cloudcafe.boot2.ms.social.app.comments.CommentsSubscriberBinding;
import org.cloudcafe.boot2.ms.social.app.images.Comment;
import org.cloudcafe.boot2.ms.social.app.images.Image;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;

@SpringBootApplication
@EnableBinding({CommentsSubscriberBinding.class, CommentPublisherBinding.class})
public class LearningSpringBoot2Chapter6StreamsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearningSpringBoot2Chapter6StreamsApplication.class, args);
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

            System.out.println(operations.findAll(Image.class));
        };
    }
}
