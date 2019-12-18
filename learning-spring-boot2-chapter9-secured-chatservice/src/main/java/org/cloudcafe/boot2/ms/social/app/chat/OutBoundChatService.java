package org.cloudcafe.boot2.ms.social.app.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Service
@Slf4j
public class OutBoundChatService extends AuthorizationHandler {


    Flux<Message<String>> chatMessageFlux;
    FluxSink<Message<String>> chatMessageSink;

    public OutBoundChatService() {
        this.chatMessageFlux = Flux.<Message<String>>create(emitter -> this.chatMessageSink = emitter,
                FluxSink.OverflowStrategy.IGNORE
        ).publish()
                .autoConnect();
    }

    @Override
    public Mono<Void> doHandle(WebSocketSession session, Principal principal) {
        return session.send(this.chatMessageFlux
                .filter(message -> validate(message,principal.getName()))
                .map(this::transform)
                .map(session::textMessage)
                .log("outbound-wrap-as-websocket-message"))
                .log("outbound-publish-to-websocket");
    }


    private String transform(Message<String> message) {
        String user = message.getHeaders()
                .get(ChatServicesStreams.USER_HEADER, String.class);

        if (message.getPayload().startsWith("@")) {
            return "(" + user + "): " + message.getPayload();
        } else {
            return "(" + user + ")(all): " + message.getPayload();
        }
    }

    private boolean validate(Message<String> message, String user) {
        if (message.getPayload().contains("@")) {
            String targetUser = message.getPayload()
                    .substring(1, message.getPayload().indexOf(" "));
            String sender = message.getHeaders()
                    .get(ChatServicesStreams.USER_HEADER, String.class);

            return user.equals(targetUser) || user.equals(sender);
        } else {
            return true;
        }
    }


    @StreamListener(ChatServicesStreams.BROKER_TO_CLIENT)
    public void listenToPublisher(Message<String> message) {
        log.info("received message from broker {}", message);
        if (chatMessageSink != null) {
            chatMessageSink.next(message);
        }
    }
}
