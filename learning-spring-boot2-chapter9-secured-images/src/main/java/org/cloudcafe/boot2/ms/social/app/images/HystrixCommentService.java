package org.cloudcafe.boot2.ms.social.app.images;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cloudcafe.boot2.ms.social.app.util.UserContextHolder;
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

    @HystrixCommand(fallbackMethod = "defaultComments", threadPoolKey = "commentsKey", threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "30"),
            @HystrixProperty(name = "maxQueueSize", value = "10")
    },
            commandKey = "commentsKey", commandProperties = {

            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"), // default 10sec
            @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "3"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"), // default 50
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000")// default 5
    }
    )
    public List<Comment> geComments(String imageId) {
        log.info("user context in hystrix {} ", UserContextHolder.getContext().getUser());
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
