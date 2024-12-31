package org.project.reactor;

import reactor.core.publisher.Mono;

public class Example03 {
    public static void main(String[] args) {
        Mono<String> mono = Mono.fromSupplier(() -> {
           throw new RuntimeException("Excepción ocurrida");
        });
        mono.subscribe(
                data -> System.out.println(data), // onNext(),
                error -> System.out.println(error), // onError
                () -> System.out.println("Terminó correctamente!") //onCOmplete
        );
    }
}
