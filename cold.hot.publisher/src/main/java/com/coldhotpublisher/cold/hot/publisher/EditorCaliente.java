package com.coldhotpublisher.cold.hot.publisher;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class EditorCaliente {
    public static void main(String[] args) throws InterruptedException {
        Flux<String> netFlux = Flux.fromStream(EditorFrio::getVideo)
                .delayElements(Duration.ofSeconds(2))
                .share();

        netFlux.subscribe(part -> System.out.println("Subscriber 1: " + part));
        Thread.sleep(5000);
        netFlux.subscribe(part -> System.out.println("Subscriber 2: " + part));
        Thread.sleep(10000);

    }
}
