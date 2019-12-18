package org.cloudcafe.boot2.ms.social.app.chapter4.services;

import lombok.AllArgsConstructor;
import org.cloudcafe.boot2.ms.social.app.chapter4.model.Image;
import org.cloudcafe.boot2.ms.social.app.chapter4.repository.ImageRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ImageService {

    private static String UPLOAD_ROOT = "UPLOAD_DIR";

    private ResourceLoader resourceLoader;

    private ImageRepository imageRepository;


    public Flux<Image> findAllImages() {
        return imageRepository.findAll();
    }

    public Mono<Resource> findOneImage(String imageName) {
        return Mono.fromSupplier(() -> resourceLoader.getResource("file:" + UPLOAD_ROOT + "/" + imageName));
    }

    public Mono<Void> createImage(Flux<FilePart> filePartFlux) {
        return filePartFlux
                .flatMap(filePart -> {
                    Mono<Image> saveImageToDB = imageRepository.save(new Image(UUID.randomUUID().toString(), filePart.filename()));
                    Mono<Void> saveFileToDisk = filePart.transferTo(Paths.get(UPLOAD_ROOT, filePart.filename()));
                    return Mono.when(saveImageToDB, saveFileToDisk);
                })
                .then();
    }

    public Mono<Void> deleteImage(String fileName) {

        return Mono.when(Mono.fromRunnable(() -> {
                    try {
                        Files.deleteIfExists(Paths.get(UPLOAD_ROOT, fileName));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).log("deleteImage-file"),
                imageRepository.findByName(fileName)
                        .log("deleteImage-find")
                        .flatMap(imageRepository::delete)
                        .log("deleteImage-record")
        ).log("deleteImage-when")
                .then()
                .log("deleteImage-done");
    }
}
