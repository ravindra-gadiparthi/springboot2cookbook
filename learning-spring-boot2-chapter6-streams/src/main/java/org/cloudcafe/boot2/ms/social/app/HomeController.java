package org.cloudcafe.boot2.ms.social.app;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cloudcafe.boot2.ms.social.app.images.CommentReaderRepository;
import org.cloudcafe.boot2.ms.social.app.images.ImageService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;

@RestController
@AllArgsConstructor
@Slf4j
public class HomeController {

    private static final String API_BASE_PATH = "/images";
    private static final String FILE_NAME = "{fileName:.+}";

    private ImageService imageService;

    private CommentReaderRepository commentRepository;


    @GetMapping("/")
    public Flux<?> findAllImages() {

        return imageService.findAllImages()
                .flatMap(image -> commentRepository.findByImageId(image.getId()).collectList()
                        .map(comments -> new HashMap<String, Object>() {
                            {
                                put("id", image.getId());
                                put("name", image.getName());
                                put("comments", comments);
                            }
                        })
                );
    }

    @GetMapping("/hello")
    public Mono<String> sayHello() {
        return Mono.just("Hello");
    }

    @GetMapping(value = API_BASE_PATH + "/" + FILE_NAME + "/raw", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public Mono<ResponseEntity<?>> findOneImage(@PathVariable String fileName) {
        return imageService.
                findOneImage(fileName)
                .map(resource -> {
                    try {
                        return ResponseEntity.ok()
                                .contentLength(resource.contentLength())
                                .body(new InputStreamResource(resource.getInputStream()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        return ResponseEntity.badRequest()
                                .body("Could not find image " + fileName);
                    }
                });
    }

    @PostMapping(value = API_BASE_PATH)
    public Mono<String> createFile(@RequestPart
                                           Flux<FilePart> files) {
        return imageService.createImage(files)
                .then(Mono.just("redirect:/"));
    }

    @DeleteMapping(value = API_BASE_PATH + "/" + FILE_NAME)
    public Mono<String> deleteImage(@PathVariable String fileName) {
        return imageService.deleteImage(fileName).then(Mono.just("redirect:/"));
    }
}
