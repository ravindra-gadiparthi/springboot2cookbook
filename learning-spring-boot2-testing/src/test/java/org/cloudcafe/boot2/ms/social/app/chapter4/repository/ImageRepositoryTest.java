package org.cloudcafe.boot2.ms.social.app.chapter4.repository;


import org.cloudcafe.boot2.ms.social.app.chapter4.model.Image;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ImageRepositoryTest {

    @Autowired
    MongoOperations operations;

    @Autowired
    ImageRepository imageRepository;

    @Before
    public void init() {
        operations.dropCollection(Image.class);

        operations.insert(new Image("1",
                "learning-spring-boot-cover.jpg"));
        operations.insert(new Image("2",
                "learning-spring-boot-2nd-edition-cover.jpg"));
        operations.insert(new Image("3",
                "bazinga.png"));

    }


    @Test
    public void testFindAll() {
        StepVerifier.create(imageRepository.findAll())
                .recordWith(() -> new ArrayList<>())
                .expectNextCount(3)
                .consumeRecordedWith(list -> {
                    assertThat(list).hasSize(3);
                    assertThat(list).extracting("id")
                            .contains("1", "2", "3");
                })
                .expectComplete()
                .verify();

    }

    @Test
    public void testFindOne() {
        StepVerifier.create(imageRepository.findByName("bazinga.png"))
                .assertNext(image -> {
                    assertThat(image.getName()).isEqualTo("bazinga.png");
                }).verifyComplete();
    }
}
