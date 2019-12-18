package org.cloudcafe.spring.boot2.social.app.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

@Component
public class InitUsers {


    @Bean
    CommandLineRunner commandLineRunner(MongoOperations operations) {
        return args -> {
            operations.dropCollection(AppUser.class);

            operations.insert(
                    new AppUser(
                            null,
                            "greg", "turnquist",
                            new String[]{"USER", "ADMIN"}));
            operations.insert(
                    new AppUser(
                            null,
                            "phil", "webb",
                            new String[]{"USER"}));

            operations.findAll(AppUser.class).forEach(user -> {
                System.out.println("Loaded " + user);
            });

        };
    }
}
