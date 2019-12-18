package org.cloudcafe.spring.boot2.social.app.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;


@AllArgsConstructor
public class SpringDataReactiveUserDetailsService implements ReactiveUserDetailsService {

    UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> User.withUsername(user.getUsername())
                        .password("{noop}" + user.getPassword())
                        .roles(user.getRoles())
                        .build());
    }
}