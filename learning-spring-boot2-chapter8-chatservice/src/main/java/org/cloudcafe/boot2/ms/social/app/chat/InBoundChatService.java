package org.cloudcafe.boot2.ms.social.app.chat;

import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import static org.cloudcafe.boot2.ms.social.app.chat.ChatServicesStreams.USER_HEADER;

@Service
public class InBoundChatService extends UserParsingHandshakeHandler {

    private final ChatServicesStreams chatServicesStreams;

    public InBoundChatService(ChatServicesStreams chatServicesStreams) {
        this.chatServicesStreams = chatServicesStreams;
    }

    @Override
    public Mono<Void> handleInternal(WebSocketSession webSocketSession) {
        return webSocketSession.receive()
                .log("inbound-incoming-chat-message")
                .map(webSocketMessage -> webSocketMessage.getPayloadAsText())
                .log("inbound-mark-with-session-id")
                .flatMap(message -> broadCast(message, getUser(webSocketSession.getId())))
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
