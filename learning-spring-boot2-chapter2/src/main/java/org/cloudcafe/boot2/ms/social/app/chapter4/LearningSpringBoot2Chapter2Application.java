package org.cloudcafe.boot2.ms.social.app.chapter4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.reactive.HiddenHttpMethodFilter;

@SpringBootApplication
public class LearningSpringBoot2Chapter2Application {

    public static void main(String[] args) {
        SpringApplication.run(LearningSpringBoot2Chapter2Application.class, args);
    }

    @Bean
    HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}
