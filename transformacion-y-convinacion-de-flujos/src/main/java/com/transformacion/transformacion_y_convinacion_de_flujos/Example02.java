package com.transformacion.transformacion_y_convinacion_de_flujos;

import reactor.core.publisher.Flux;

public class Example02 {
    public static void main(String[] args) {
        Flux.fromArray(new String[]{"Tom", "Melisa", "Ludwin", "Steve", "Megan"})
                .filter(nombre -> nombre.length() > 5)
                .map(String::toUpperCase)
                .subscribe(System.out::println);
    }
}
