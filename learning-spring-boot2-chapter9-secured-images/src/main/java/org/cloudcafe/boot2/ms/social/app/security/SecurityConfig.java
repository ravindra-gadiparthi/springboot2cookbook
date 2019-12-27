package org.cloudcafe.boot2.ms.social.app.security;

import org.cloudcafe.boot2.ms.social.app.filter.UserContextFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;

import static org.springframework.security.config.web.server.SecurityWebFiltersOrder.LAST;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Autowired
    UserContextFilter contextFilter;

    @Bean
    SecurityWebFilterChain securityWebFilterChain() {
        return ServerHttpSecurity.http()
                .authorizeExchange().anyExchange().authenticated()
                .and()
                .securityContextRepository(new WebSessionServerSecurityContextRepository())
                .addFilterAfter(contextFilter, LAST)
                .cors().disable()
                .csrf().disable()
                .build();
    }
}
