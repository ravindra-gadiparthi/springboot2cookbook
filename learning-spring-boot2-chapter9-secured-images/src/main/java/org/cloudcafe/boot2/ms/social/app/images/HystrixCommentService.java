package org.cloudcafe.boot2.ms.social.app.images;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class HystrixCommentService {

    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "defaultComments")
    public List<Comment> geComments(String imageId) {
        return restTemplate.exchange(
                "http://comments/api/comments/{imageId}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Comment>>() {
                }, imageId).getBody();
    }

    public List<Comment> defaultComments(String imageId) {
        log.info("executing fall back method");
        return new ArrayList<>();
    }
}
