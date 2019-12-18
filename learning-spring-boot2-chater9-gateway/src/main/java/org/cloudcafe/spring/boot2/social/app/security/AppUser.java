package org.cloudcafe.spring.boot2.social.app.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    @Id
    private String id;
    private String username;
    private String password;
    private String[] roles;
}
