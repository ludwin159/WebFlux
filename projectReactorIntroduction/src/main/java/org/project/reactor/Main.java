package org.project.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        List<Integer> elementsFromMono = new ArrayList<>();
        List<Integer> elementsFromFlux = new ArrayList<>();

        Mono<Integer> mono = Mono.just(1);
        Flux<Integer> flux = Flux.just(10, 20, 30, 150);

        mono.subscribe(elementsFromMono::add);
        flux.subscribe(elementsFromFlux::add);

        System.out.println(elementsFromMono);
        System.out.println(elementsFromFlux);
    }
}