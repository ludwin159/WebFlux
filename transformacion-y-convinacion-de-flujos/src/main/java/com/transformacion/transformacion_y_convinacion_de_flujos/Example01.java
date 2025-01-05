package com.transformacion.transformacion_y_convinacion_de_flujos;

import reactor.core.publisher.Flux;

public class Example01 {
    public static void main(String[] args) {
        Flux.fromArray(new String[]{"Tom", "Melisa", "Ludwin", "Steve", "Megan"})
                .map(String::toUpperCase)
                .log()
                .subscribe(System.out::println);
    }
}
