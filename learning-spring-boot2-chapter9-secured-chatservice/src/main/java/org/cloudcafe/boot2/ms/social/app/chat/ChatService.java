package org.cloudcafe.boot2.ms.social.app.chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Service
@EnableBinding(Sink.class)
@Slf4j
public class ChatService extends AuthorizationHandler {

    private ObjectMapper objectMapper;
    private Flux<Comment> commentFlux;
    private FluxSink<Comment> websocketSink;

    public ChatService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.commentFlux = Flux.<Comment>create(emitter -> this.websocketSink = emitter
                , FluxSink.OverflowStrategy.IGNORE
        ).publish().autoConnect();
    }

    @StreamListener(Sink.INPUT)
    public void broadCast(Comment comment) {
        log.info("invoking message publish");
        if (websocketSink != null) {
            log.info("Publishing Comment {} to websocket", comment);
            websocketSink.next(comment);
        }
    }

    @Override
    public Mono<Void> doHandle(WebSocketSession webSocketSession, Principal principal) {
        return webSocketSession.send((commentFlux)
                .map(comment -> {
                    try {
                        return objectMapper.writeValueAsString(comment);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Failed to convert comment");
                    }
                })
                .log("encoded as string")
                .map(message -> webSocketSession.textMessage(message))
                .log("wrap as websocket message")
        ).log("publishing to websocket message");
    }
}
