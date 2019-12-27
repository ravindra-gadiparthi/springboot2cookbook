package org.cloudcafe.boot2.ms.social.app.util;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserContext {
    public static final String name = "username";

    private String user;
}
