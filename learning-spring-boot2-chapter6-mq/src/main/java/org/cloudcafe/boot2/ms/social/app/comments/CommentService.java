package org.cloudcafe.boot2.ms.social.app.comments;


import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CommentService {

    CommentWriterRepository commentRepository;
    MeterRegistry meterRegistry;

    @RabbitListener(bindings =
    @QueueBinding(value = @Queue,
            exchange = @Exchange(value = "learning-spring-boot"),
            key = "comment.new"))
    public void save(Comment comment) {
               commentRepository.save(comment)
                .log("comment saved in listener")
                .flatMap(res -> Mono.fromRunnable(() ->
                        meterRegistry.counter("comments.consumed","imageId", comment.getImageId()).increment()))
                .subscribe();
    }

    @Bean
    Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
