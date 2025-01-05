import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class EjemplosTests {
    @Test
    public void testFlux() {
        Flux<Integer> fluxToTest = Flux.just(1,2,3,4,5);

        StepVerifier.create(fluxToTest)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .expectNext(5)
                .expectComplete()
                .verify();
    }

    @Test
    public void testFluxString() {
        Flux<String> fluxToText = Flux.just(
                "Jessica", "Tomas", "Ludwin", "John")
                .filter(name -> name.length() > 5 )
                .map(String::toUpperCase);

        StepVerifier.create(fluxToText)
                .expectNext("JESSICA")
                .expectNext("LUDWIN").expectComplete().verify();

    }
}
