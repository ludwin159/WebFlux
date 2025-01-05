package com.coldhotpublisher.cold.hot.publisher;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

public class EditorFrio {
    public static void main(String[] args) throws InterruptedException {
        Flux<String> netFlux = Flux.fromStream(EditorFrio::getVideo)
                .delayElements(Duration.ofSeconds(2));

        netFlux.subscribe(part -> System.out.println("Subscriber 1: " + part));

        Thread.sleep(5000);

        netFlux.subscribe(part -> System.out.println("Subscriber 2:" + part ));

        Thread.sleep(60000);
    }

    public static Stream<String> getVideo() {
        System.out.println("Request para el video");
        return Stream.of("part 1", "part 2", "part 3", "part 4", "part 5");
    }
}
