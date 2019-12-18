package org.cloudcafe.boot2.ms.social.app.chat;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.security.Principal;

public abstract class AuthorizationHandler implements WebSocketHandler {

    @Override
    public Mono<Void> handle(WebSocketSession session) {

        return session.getHandshakeInfo().getPrincipal()
                .filter(this::isAuthenticated)
                .flatMap(principal -> doHandle(session, principal));
    }

    abstract Mono<Void> doHandle(WebSocketSession session, Principal principal);

    private boolean isAuthenticated(Principal principal) {
        Authentication authentication = (Authentication) principal;
        return authentication.isAuthenticated() && authentication
                .getAuthorities()
                .stream()
                .anyMatch(role ->
                        "ROLE_USER".equals(role.getAuthority()));
    }


}
