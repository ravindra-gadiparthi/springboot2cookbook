package org.cloudcafe.boot2.ms.social.app.chapter4;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class ReactorTest {


    @Test
    public void reactorPractice() {

        Flux.just("alpha", "bravo", "charlie")
                .map(String::toUpperCase)
                .flatMap(s -> Flux.fromArray(s.split("")))
                .groupBy(String::toString)
                .sort((o1, o2) -> Objects.requireNonNull(o1.key()).compareTo(o2.key()))
                .flatMap(group -> Mono.just(group.key())
                        .flatMap(key -> group.count())
                        .map(count -> group.key() + " => " + count)
                )
                .subscribe(System.out::println);

    }
}
