import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class EjemplosTests {

    @Test
    public  void transformMap() {
        List<String> listaNombres = Arrays.asList("Google", "abc", "fb", "stackoverflow");
        Flux<String> nombresFlux = Flux.fromIterable(listaNombres)
                .filter(name -> name.length()>5)
                .map(String::toUpperCase)
                .log();

        StepVerifier.create(nombresFlux)
                .expectNext("GOOGLE", "STACKOVERFLOW")
                .verifyComplete();
    }

    @Test
    public void testTransformFlatmap() {
        List<String> listaNombres = Arrays.asList("Google", "abc", "fb", "stackoverflow");
        Flux<String> nombresFlux = Flux.fromIterable(listaNombres)
                .filter(name -> name.length()>5)
                .flatMap(name -> {
                    return Mono.just(name.toUpperCase());
                })
                .log();

        StepVerifier.create(nombresFlux)
                .expectNext("GOOGLE", "STACKOVERFLOW")
                .verifyComplete();
    }

    @Test
    public void testsCombinarFlujosUsandoMerge() {
        Flux<String> flux1 = Flux.just("Blenders", "Old", "Johnnia");
        Flux<String> flux2 = Flux.just("Pride", "Monk", "Balker");

        Flux<String> fluxMerge = Flux.merge(flux1, flux2).log();

        StepVerifier.create(fluxMerge)
                .expectNext("Blenders", "Old", "Johnnia", "Pride", "Monk", "Balker")
                .verifyComplete();
    }
}
