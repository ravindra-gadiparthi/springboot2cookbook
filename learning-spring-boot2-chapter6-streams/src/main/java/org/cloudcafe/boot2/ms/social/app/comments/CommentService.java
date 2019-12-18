package org.cloudcafe.boot2.ms.social.app.comments;


import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static org.cloudcafe.boot2.ms.social.app.comments.CommentsSubscriberBinding.INPUT;

@Service
@AllArgsConstructor
public class CommentService {

    CommentWriterRepository commentRepository;

    MeterRegistry meterRegistry;

    @StreamListener(target = INPUT)
    public void save(Comment comment) {
        commentRepository.save(comment)
                .log("comment saved in listener")
                .flatMap(res -> Mono.fromRunnable(() ->
                        meterRegistry.counter("comments.consumed", "imageId", comment.getImageId()).increment()))
                .subscribe();
    }

    @Bean
    Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
