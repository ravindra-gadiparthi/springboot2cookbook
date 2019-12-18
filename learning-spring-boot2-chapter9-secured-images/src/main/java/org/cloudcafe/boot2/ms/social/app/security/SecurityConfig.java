package org.cloudcafe.boot2.ms.social.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {


    @Bean
    SecurityWebFilterChain securityWebFilterChain() {
        return ServerHttpSecurity.http()
                .authorizeExchange().anyExchange().authenticated()
                .and()
                .securityContextRepository(new WebSessionServerSecurityContextRepository())
                .cors().disable()
                .csrf().disable()
                .build();
    }
}
