package org.cloudcafe.spring.boot2.social.app.security;

import org.springframework.data.repository.Repository;
import reactor.core.publisher.Mono;

public interface UserRepository extends Repository<AppUser, String> {

    Mono<AppUser> findByUsername(String username);
}
