package org.cloudcafe.boot2.ms.social.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class LearningSpringBoot2Chapter7EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearningSpringBoot2Chapter7EurekaServerApplication.class, args);
    }

}
