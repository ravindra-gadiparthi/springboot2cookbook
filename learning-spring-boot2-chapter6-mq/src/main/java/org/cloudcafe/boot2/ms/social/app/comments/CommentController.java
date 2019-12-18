package org.cloudcafe.boot2.ms.social.app.comments;


import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
public class CommentController {

    RabbitMessagingTemplate template;
    MeterRegistry meterRegistry;


    @PostMapping(value = "/api/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity> saveComment(@RequestBody Mono<Comment> newComment) {
        return newComment.flatMap(comment ->
                Mono.fromRunnable(() ->
                        template.convertAndSend("learning-spring-boot",
                                "comment.new",
                                comment))
                        .log("comment service publish")
                        .thenReturn(comment)
                ).flatMap(comment -> Mono.fromRunnable(() -> {
                    meterRegistry.counter("comments.produced", "imageId",comment.getImageId()).increment();
                }))
                .thenReturn(ResponseEntity.accepted().build());
    }

}
