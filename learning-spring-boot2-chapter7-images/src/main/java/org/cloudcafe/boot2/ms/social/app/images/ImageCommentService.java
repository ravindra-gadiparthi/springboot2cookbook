package org.cloudcafe.boot2.ms.social.app.images;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class ImageCommentService {

    HystrixCommentService commentService;

    public Flux<Comment> getCommentsByImageId(String imageId) {
        return Mono.defer(() -> Mono.just(commentService.geComments(imageId)))
                .flatMapIterable(list -> list)
                .flatMap(list -> Flux.just(list));
    }
}
