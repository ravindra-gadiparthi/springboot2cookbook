package org.cloudcafe.boo2.ms.social.app.resources;


import lombok.AllArgsConstructor;
import org.cloudcafe.boo2.ms.social.app.model.Chapter;
import org.cloudcafe.boo2.ms.social.app.repository.ChapterRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@AllArgsConstructor
public class ChapterController {

    private ChapterRepo chapterRepo;

    @GetMapping("/chapters")
    public Flux<Chapter> getChapters() {
        return chapterRepo.findAll();
    }
}
