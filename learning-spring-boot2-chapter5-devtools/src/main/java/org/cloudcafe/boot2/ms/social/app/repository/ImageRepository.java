package org.cloudcafe.boot2.ms.social.app.repository;

import org.cloudcafe.boot2.ms.social.app.model.Image;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ImageRepository extends ReactiveCrudRepository<Image, String> {

    Mono<Image> findByName(String name);
}
