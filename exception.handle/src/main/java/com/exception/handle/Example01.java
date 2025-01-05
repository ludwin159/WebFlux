package com.exception.handle;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Example01 {
    public static void main(String[] args) {
        Flux.just(2, 7, 10)
                .concatWith(Flux.error(new RuntimeException("Exception ocurred")))
                .concatWith(Mono.just(12))
                .onErrorReturn(72)
                .log()
                .subscribe();
    }
}
