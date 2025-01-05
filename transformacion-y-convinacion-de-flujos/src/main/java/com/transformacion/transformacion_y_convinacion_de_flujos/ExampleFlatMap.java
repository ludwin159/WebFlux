package com.transformacion.transformacion_y_convinacion_de_flujos;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ExampleFlatMap {
    public static void main(String[] args) {
        Flux.fromArray(new String[]{"Tom", "Melisa", "Ludwin", "Steve", "Megan"})
                .flatMap(ExampleFlatMap::nameToMono)
                .subscribe(System.out::println);
    }

    private static Mono<String> nameToMono(String name) {
        return Mono.just(name.concat(" Modificado"));
    }
}
