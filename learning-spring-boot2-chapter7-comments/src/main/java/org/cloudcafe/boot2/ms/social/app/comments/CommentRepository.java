package org.cloudcafe.boot2.ms.social.app.comments;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CommentRepository extends ReactiveCrudRepository<Comment, String> {

    Flux<Comment> findByImageId(String id);

    Mono<Comment> save(Mono<Comment> comment);
}
