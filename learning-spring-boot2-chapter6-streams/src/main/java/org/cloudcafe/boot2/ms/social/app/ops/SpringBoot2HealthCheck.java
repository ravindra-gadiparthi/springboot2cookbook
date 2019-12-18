package org.cloudcafe.boot2.ms.social.app.ops;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;

@Component("ServiceHealth")
public class SpringBoot2HealthCheck implements HealthIndicator {

    @Override
    public Health health() {

        try {
            URL uri = new URL("https://learning-spring-boot2.cfapps.io/actuator/health");
            HttpURLConnection urlConnection = (HttpURLConnection) uri.openConnection();
            if (HttpStatus.valueOf(urlConnection.getResponseCode()).is2xxSuccessful()) {
                return Health.up().withDetail("BACKEND","UP").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Health.down(e).build();
        }
        return Health.down().build();
    }
}
