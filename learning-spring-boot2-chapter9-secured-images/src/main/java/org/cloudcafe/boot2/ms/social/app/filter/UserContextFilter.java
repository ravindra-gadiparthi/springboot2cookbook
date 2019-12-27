package org.cloudcafe.boot2.ms.social.app.filter;

import lombok.extern.slf4j.Slf4j;
import org.cloudcafe.boot2.ms.social.app.util.UserContextHolder;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


@Component
@Slf4j
public class UserContextFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {


        return ReactiveSecurityContextHolder.getContext().map(context -> {
            log.info("in filter {}", context.getAuthentication());
            UserContextHolder.getContext().setUser(context.getAuthentication().getName());
            return UserContextHolder.getContext();
        }).then(webFilterChain.filter(serverWebExchange));
    }

}
