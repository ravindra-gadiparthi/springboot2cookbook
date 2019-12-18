package org.cloudcafe.spring.boot2.social.app.security;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserDetailsController {

    @GetMapping("/userdetails")
    public HashMap<String, Object> getUserDetails(Authentication authentication) {


        if (authentication.getAuthorities() != null) {
            List<String> roles = authentication.getAuthorities().stream()
                    .map(role -> ((GrantedAuthority) role).getAuthority())
                    .collect(Collectors.toList());
            return new HashMap() {{
                put("name", authentication.getName());
                put("roles", roles);
            }};
        } else {
            return new HashMap() {{
                put("name", authentication.getName());
            }};
        }
    }
}
