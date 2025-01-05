import com.step.verifier.services.ServicioSencillo;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

public class PruebasServiciosStepVerifierApplicationTests {
    private final ServicioSencillo servicioSencillo = new ServicioSencillo();

    @Test
    public void testMono() {
        Mono<String> uno = servicioSencillo.buscarUno();
        StepVerifier.create(uno)
                .expectNext("Hola")
                .verifyComplete();
    }

    @Test
    public void testVarios() {
        Flux<String> varios = servicioSencillo.buscarTodos();
        StepVerifier.create(varios)
                .expectNext("Hola")
                .expectNext("que")
                .expectNext("tal")
                .expectNext("estas")
                .verifyComplete();
    }

    @Test
    public void testVariosLenta() {
        Flux<String> varios = servicioSencillo.buscarTodosLento();
        StepVerifier.create(varios)
                .expectNext("Hola")
                .thenAwait()
                .expectNext("que")
                .thenAwait()
                .expectNext("tal")
                .thenAwait()
                .expectNext("estas").verifyComplete();
    }
}
