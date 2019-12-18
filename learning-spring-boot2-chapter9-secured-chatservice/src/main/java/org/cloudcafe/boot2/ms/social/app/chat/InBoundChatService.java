package org.cloudcafe.boot2.ms.social.app.chat;

import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.security.Principal;

import static org.cloudcafe.boot2.ms.social.app.chat.ChatServicesStreams.USER_HEADER;

@Service
public class InBoundChatService extends AuthorizationHandler {

    private final ChatServicesStreams chatServicesStreams;

    public InBoundChatService(ChatServicesStreams chatServicesStreams) {
        this.chatServicesStreams = chatServicesStreams;
    }

    @Override
    public Mono<Void> doHandle(WebSocketSession webSocketSession, Principal principal) {
        return webSocketSession.receive()
                .log("inbound-incoming-chat-message")
                .map(webSocketMessage -> webSocketMessage.getPayloadAsText())
                .log("inbound-mark-with-session-id")
                .flatMap(message -> broadCast(message, principal.getName()))
                .log("inbound-broadcast-to-broker")
                .then();

    }

    public Mono<?> broadCast(String message, String username) {
        return Mono.fromRunnable(() -> {
            System.out.println("publishing message");
            chatServicesStreams.clientToBroker()
                    .send(MessageBuilder.withPayload(message)
                            .setHeader(USER_HEADER, username)
                            .build());
        });
    }
}
