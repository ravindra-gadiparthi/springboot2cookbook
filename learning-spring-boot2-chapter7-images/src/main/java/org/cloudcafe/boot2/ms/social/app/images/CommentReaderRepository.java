package org.cloudcafe.boot2.ms.social.app.images;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CommentReaderRepository extends ReactiveCrudRepository<org.cloudcafe.boot2.ms.social.app.comments.Comment, String> {
    Flux<Comment> findByImageId(String imageId);
}
