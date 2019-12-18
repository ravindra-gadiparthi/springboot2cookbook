package org.cloudcafe.boot2.ms.social.app.chat;


import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface ChatServicesStreams {

    String NEW_COMMENTS = "newComments";
    String CLIENT_TO_BROKER = "clientToBroker";
    String BROKER_TO_CLIENT = "brokerToClient";
    String USER_HEADER="user";

    @Input(NEW_COMMENTS)
    SubscribableChannel newComments();

    @Output(CLIENT_TO_BROKER)
    MessageChannel clientToBroker();

    @Input(BROKER_TO_CLIENT)
    SubscribableChannel brokerToClient();
}
