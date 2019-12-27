package org.cloudcafe.boot2.ms.social.app.util;

import java.util.concurrent.Callable;

public class DelegationUserContextCallable<V> implements Callable<V> {

    private UserContext userContext;
    private final Callable<V> callable;

    public DelegationUserContextCallable(Callable callable, UserContext userContext) {
        this.userContext = userContext;
        this.callable = callable;
    }

    @Override
    public V call() throws Exception {
        try {

            UserContextHolder.setContext(userContext);
            return callable.call();
        } catch (Exception e) {
            throw e;
        }
    }
}
