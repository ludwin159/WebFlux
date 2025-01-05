package com.api_rest_reactiva.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/mono")
    public Mono<String> getMono() {
         return Mono.just("Bienvenidos perros!");
    }

    @GetMapping(path = "/flux")
    public Flux<String> getFlux() {
        return Flux.just("Bienvenido", "a", "WEB", "flux")
                .delayElements(Duration.ofSeconds(1)).log();
    }
}
