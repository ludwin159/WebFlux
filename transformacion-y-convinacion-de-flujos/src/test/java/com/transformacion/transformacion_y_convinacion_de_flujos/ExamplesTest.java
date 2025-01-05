package com.transformacion.transformacion_y_convinacion_de_flujos;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class ExamplesTest {
    @Test
    public void transformMap() {
        List<String> names = Arrays.asList("Google", "abc", "fb", "stackOverflow");
        Flux<String> namesFlux = Flux.fromIterable(names)
                .filter(name -> name.length()>5)
                .map(String::toUpperCase)
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("GOOGLE")
                .expectNext("STACKOVERFLOW")
                .verifyComplete();
    }

    @Test
    public  void testTransformUsingFlatMap() {
        List<String> names = Arrays.asList("Google", "abc", "fb", "stackOverflow");
        Flux<String> namesFlux = Flux.fromIterable(names)
                .filter(name -> name.length()>5)
                .flatMap(name -> {
                    return Mono.just(name.toUpperCase());
                })
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("GOOGLE")
                .expectNext("STACKOVERFLOW")
                .verifyComplete();
    }

    @Test
    public void testCombinarFlujosUsandoMerge() {
        Flux<String> flux1 = Flux.just("Blenders", "Old", "Johnnie");
        Flux<String> flux2 = Flux.just("Pride", "Monk", "Walker");

        Flux<String> fluxMerge = Flux.merge(flux1, flux2);
        StepVerifier.create(fluxMerge)
                .expectNext("Blenders", "Old", "Johnnie", "Pride", "Monk", "Walker")
                .verifyComplete();
    }
    @Test
    public void testCombinarFlujosUsandoMergeWithDelay() {
        Flux<String> flux1 = Flux.just("Blenders", "Old", "Johnnie")
                .delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("Pride", "Monk", "Walker", "Julian")
                .delayElements(Duration.ofSeconds(1));

        Flux<String> fluxMerge = Flux.merge(flux1, flux2).log();

        StepVerifier.create(fluxMerge)
                .expectSubscription()
                .expectNextCount(7)
                .verifyComplete();
    }

    @Test
    public void testCombinarFlujosUsandoDemoraYConcat() {
        Flux<String> flux1 = Flux.just("Blenders", "Old", "Johnnie")
                .delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("Pride", "Monk", "Walker", "Julian")
                .delayElements(Duration.ofSeconds(1));

        Flux<String> fluxConcat = Flux.concat(flux1, flux2).log();

        StepVerifier.create(fluxConcat)
                .expectSubscription()
                .expectNext("Blenders", "Old", "Johnnie", "Pride", "Monk", "Walker", "Julian")
                .verifyComplete();
    }

    @Test
    public void testCombinarFlujosUsandoDemoraYZIP() {
        Flux<String> flux1 = Flux.just("Blenders", "Old", "Johnnie")
                .delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("Pride", "Monk", "Walker", "Julian")
                .delayElements(Duration.ofSeconds(1));

        Flux<String> fluxZip = Flux.zip(flux1, flux2, (f1, f2) -> {
            return f1.concat(" ").concat(f2);
        }).log();

        StepVerifier.create(fluxZip)
                .expectNext("Blenders Pride", "Old Monk", "Johnnie Walker")
                .verifyComplete();
    }
}
