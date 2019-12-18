package org.cloudcafe.boot2.ms.social.app.resources;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cloudcafe.boot2.ms.social.app.model.Image;
import org.cloudcafe.boot2.ms.social.app.services.ImageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@Slf4j
public class WelcomeController {

    private static final String API_BASE_PATH = "/api";

    ImageService imageService;

    @GetMapping(API_BASE_PATH + "/images")
    Flux<Image> findAllImages() {
        return imageService.findAllImages();
    }

    @PostMapping(API_BASE_PATH + "/images")
    Mono<Void> create(@RequestBody Flux<Image> images) {
        return images
                .map(image -> {
                    log.info("We will save " + image +
                            " to a Reactive database soon!");
                    return image;
                })
                .then();
    }


}
