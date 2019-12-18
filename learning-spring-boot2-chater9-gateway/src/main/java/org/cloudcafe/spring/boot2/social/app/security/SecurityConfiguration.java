package org.cloudcafe.spring.boot2.social.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity.authorizeExchange().pathMatchers("/**")
                .authenticated()
                .and()
                .csrf().disable()
                .cors().disable()
                .httpBasic().and()
                .formLogin();
        return serverHttpSecurity.build();
    }

    @Bean
    SpringDataReactiveUserDetailsService dataReactiveUserDetailsService(UserRepository userRepository) {
        return new SpringDataReactiveUserDetailsService(userRepository);
    }
}






