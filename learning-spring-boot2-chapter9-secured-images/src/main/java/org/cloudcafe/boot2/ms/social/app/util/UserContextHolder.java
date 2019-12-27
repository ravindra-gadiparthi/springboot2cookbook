package org.cloudcafe.boot2.ms.social.app.util;

import org.springframework.util.Assert;


public class UserContextHolder {

    public static ThreadLocal<UserContext> contextThreadLocal = new ThreadLocal<>();

    public static final UserContext getContext() {
        UserContext userContext = contextThreadLocal.get();

        if (userContext == null) {
            userContext = createEmptyContext();
            setContext(userContext);
        }
        return userContext;
    }


    public static void setContext(UserContext context) {
        Assert.notNull(context, "Context Should not be null");
        contextThreadLocal.set(context);
    }

    private static UserContext createEmptyContext() {
        return new UserContext();
    }
}
