package com.transformacion.transformacion_y_convinacion_de_flujos;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class PracticeTest {

    @Test
    public void testMergeWith() {
        StepVerifier.create(returnMerged())
                .expectNext("a")
                .expectNext("c")
                .expectNext("b")
                .expectNext("d")
                .expectComplete()
                .verify();
    }

    private static Flux<String> returnMerged() {
        Flux<String> firstFlux = Flux.fromArray(new String[] {"a", "b"})
                .delayElements(Duration.ofMillis(100));
        Flux<String> sencondFlux = Flux.fromArray(new String[] {"c", "d"})
                .delayElements(Duration.ofMillis(100));

        return firstFlux.mergeWith(sencondFlux);
    }
}
