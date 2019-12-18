package org.cloudcafe.boot2.ms.social.app.comments;


import org.cloudcafe.boot2.ms.social.app.images.ImageService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

@Profile("simulator")
@Component
public class CommentsSimulator {

    final CommentController commentController;

    final ImageService imageService;

    final AtomicInteger counter;

    public CommentsSimulator(CommentController commentController, ImageService imageService) {
        this.commentController = commentController;
        this.imageService = imageService;
        this.counter = new AtomicInteger(1);
    }

    @EventListener
    public void onApplicationReadyEvent(ApplicationReadyEvent readyEvent) {

        Flux.interval(Duration.ofMillis(10000))
                .flatMap(value -> imageService.findAllImages())
                .map(image -> Comment.builder().comment("Comment #" + counter.getAndIncrement())
                        .imageId(image.getId()).build()
                ).map(comment -> Mono.just(comment))
                .flatMap(comment -> Mono.defer(() ->
                        commentController.saveComment(comment)))
                .subscribe();

    }

}
