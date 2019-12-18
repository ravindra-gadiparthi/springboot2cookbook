package org.cloudcafe.boo2.ms.social.app.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping("/")
    public ResponseEntity<String> sayHello(@RequestParam(value = "name", defaultValue = "CloudCafe") String name) {
        return ResponseEntity.ok("Hello " + name);
    }

}
