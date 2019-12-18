package org.cloudcafe.boot2.ms.social.app.comments;


import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static org.springframework.cloud.stream.messaging.Source.OUTPUT;

@Service
@AllArgsConstructor
@Slf4j
@EnableBinding(Processor.class)
public class CommentService {

    CommentRepository commentRepository;

    MeterRegistry meterRegistry;

    @StreamListener(Processor.INPUT)
    @Output(OUTPUT)
    public Comment save(Comment comment) {
        return commentRepository.save(comment)
                .flatMap(res -> {
                            log.info("comment saved " + comment);
                            meterRegistry.counter("comments.consumed", "imageId", res.getImageId()).increment();
                            return Mono.just(res);
                        }
                ).block();
    }


    @Bean
    Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
