package com.coldhotpublisher.cold.hot.publisher;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class EditorCalienteRefCount {
    public static void main(String[] args) throws InterruptedException {
        Flux<String> netFlux = Flux.fromStream(EditorFrio::getVideo)
                .delayElements(Duration.ofSeconds(2))
                .publish()
                .refCount(2);

        netFlux.subscribe(part -> System.out.println("Subscriber 1: " + part));
        netFlux.subscribe(part -> System.out.println("Subscriber 2: " + part));
        Thread.sleep(5000);
        netFlux.subscribe(part -> System.out.println("Subscriber 3: " + part));
        Thread.sleep(10000);

    }
}
