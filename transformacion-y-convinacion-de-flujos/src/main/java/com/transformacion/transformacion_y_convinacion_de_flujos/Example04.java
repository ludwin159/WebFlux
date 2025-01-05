package com.transformacion.transformacion_y_convinacion_de_flujos;

import reactor.core.publisher.Flux;

public class Example04 {
    public static void main(String[] args) {
        Flux<String> firstFlux = Flux.fromArray(new String[]{"a", "b", "c"});
        Flux<String> secondFlux = Flux.fromArray(new String[]{"d", "e", "f"});

        Flux<String> combinaFlux = Flux.merge(firstFlux, secondFlux).log();
        combinaFlux.subscribe();
    }
}
