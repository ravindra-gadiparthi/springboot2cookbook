package org.cloudcafe.boot2.ms.social.app.comments;


import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@AllArgsConstructor
public class CommentController {

    public static final String API_COMMENTS = "/api/comments";

    CommentRepository commentRepository;

    @GetMapping(value = API_COMMENTS + "/{imageId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Comment> getComments(@PathVariable String imageId) {
        return commentRepository.findByImageId(imageId);
    }
}
