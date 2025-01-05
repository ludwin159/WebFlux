package com.transferencia.combinacion;

import reactor.core.publisher.Flux;

public class Ejemplo02 {
    public static void main(String[] args) {
        Flux.fromArray(new String[]{"Tom", "Melisa", "Steve", "Megan"})
                .filter(name -> name.length()>3)
                .map(String::toUpperCase)
                .subscribe(System.out::println);

    }
}
