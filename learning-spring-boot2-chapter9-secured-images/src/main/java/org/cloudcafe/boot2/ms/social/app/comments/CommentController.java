package org.cloudcafe.boot2.ms.social.app.comments;


import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

@RestController
public class CommentController {

    MeterRegistry meterRegistry;
    MessageChannel commentsChannel;

    public CommentController(MeterRegistry meterRegistry, Processor processor) {
        this.meterRegistry = meterRegistry;
        this.commentsChannel = processor.output();
    }

    @PostMapping(value = "/api/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity> saveComment(@RequestBody Mono<Comment> newComment) {
        return newComment.flatMap(comment ->
                Mono.fromRunnable(() ->
                        commentsChannel.send(MessageBuilder.withPayload(comment).build()))
                        .log("comment service publish")
                        .thenReturn(comment)
        ).flatMap(comment -> Mono.fromRunnable(() -> {
            meterRegistry.counter("comments.produced", "imageId", comment.getImageId()).increment();
        }))
                .thenReturn(ResponseEntity.accepted().build());
    }

}
