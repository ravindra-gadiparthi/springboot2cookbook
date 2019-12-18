package org.cloudcafe.boot2.ms.social.app.chapter4.resources;


import org.cloudcafe.boot2.ms.social.app.chapter4.model.Image;
import org.cloudcafe.boot2.ms.social.app.chapter4.repository.ImageRepository;
import org.cloudcafe.boot2.ms.social.app.chapter4.services.ImageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebFluxTest(controllers = ImageController.class)
public class ImageControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    ImageService imageService;


    @Test
    public void testFindAllImages() {

        Image bravo = new Image("1", "bingo.jpg");
        given(imageService.findAllImages()).willReturn(Flux.just(bravo));
        webTestClient.get()
                .uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Image.class)
                .consumeWith((list) -> {
                    assertThat(list)
                            .extracting("body")
                            .asList()
                            .hasSize(1)
                            .contains(bravo);
                })
                .contains(bravo);

        verify(imageService).findAllImages();
        verifyNoMoreInteractions(imageService);


    }

    @Test
    public void testFindOneImage() {
        String data = "data";
        given(imageService.findOneImage(anyString()))
                .willReturn(Mono.just(new ByteArrayResource(data.getBytes())));

        webTestClient.get()
                .uri("/images/data.jpg/raw")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(data);

        verify(imageService).findOneImage("data.jpg");
        verifyNoMoreInteractions(imageService);
    }

    @Test
    public void fetchingNullImageShouldFail() throws Exception {
        Resource resource = mock(Resource.class);
        given(resource.getInputStream())
                .willThrow(new IOException("Bad file"));
        given(imageService.findOneImage(any())).willReturn(Mono.just(resource));

        webTestClient.get()
                .uri("/images/data.jpg/raw")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .isEqualTo("Could not find image data.jpg");


        verify(imageService).findOneImage("data.jpg");
        verifyNoMoreInteractions(imageService);
    }


    @Test
    public void testDeleteImage() {
        given(imageService.deleteImage(anyString())).willReturn(Mono.empty());

        webTestClient.delete()
                .uri("/images/data.jpg")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("redirect:/");
    }
}
