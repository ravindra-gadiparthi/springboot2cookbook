package org.cloudcafe.boot2.ms.social.app.chapter4.services;

import lombok.AllArgsConstructor;
import org.cloudcafe.boot2.ms.social.app.chapter4.model.Image;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
public class ImageService {

    private static String UPLOAD_ROOT = "UPLOAD_DIR";

    private ResourceLoader resourceLoader;


    public Flux<Image> findAllImages() {
        try {
            return Flux.fromIterable(Files.newDirectoryStream(Paths.get(UPLOAD_ROOT)))
                    .map(path -> new Image(String.valueOf(path.hashCode()), path.getFileName().toString()));
        } catch (IOException e) {
            e.printStackTrace();
            return Flux.empty();
        }
    }

    public Mono<Resource> findOneImage(String imageName) {
        return Mono.fromSupplier(() -> resourceLoader.getResource("file:" + UPLOAD_ROOT + "/" + imageName));
    }

    public Mono<Void> createImage(Flux<FilePart> filePartFlux) {
        return filePartFlux
                .flatMap(filePart -> filePart.transferTo(Paths.get(UPLOAD_ROOT, filePart.filename())))
                .then();
    }

    public Mono<Void> deleteImage(String fileName) {
        return Mono.fromRunnable(() -> {
            try {
                Files.deleteIfExists(Paths.get(UPLOAD_ROOT, fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Bean
    private CommandLineRunner init() {
        return (args) -> {
            FileSystemUtils.deleteRecursively(new File(UPLOAD_ROOT));

            Files.createDirectories(Paths.get(UPLOAD_ROOT));

            FileCopyUtils.copy("TestFile1",
                    new FileWriter(UPLOAD_ROOT + "/learning-spring-boot-cover.jpg"));

            FileCopyUtils.copy("TestFile2",
                    new FileWriter(UPLOAD_ROOT + "/learning-spring-boot-2nd-edition-cover.jpg"));

            FileCopyUtils.copy("TestFile3",
                    new FileWriter(UPLOAD_ROOT + "/bazinga.png"));

        };
    }
}
