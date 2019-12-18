package org.cloudcafe.boo2.ms.social.app.config;

import org.cloudcafe.boo2.ms.social.app.model.Chapter;
import org.cloudcafe.boo2.ms.social.app.repository.ChapterRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner runner(ChapterRepo chapterRepo) {

        chapterRepo.deleteAll().subscribe();
        return (args) -> Flux.just(new Chapter("Chapter 1"),
                new Chapter("Chapter 2"),
                new Chapter("Chapter 3"))
                .flatMap(chapter -> chapterRepo.save(chapter))
                .subscribe(System.out::println);
    }
}
