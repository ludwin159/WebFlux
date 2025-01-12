package com.api_rest_reactiva.webflux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class HelloRouter {

    @Bean
    public RouterFunction<ServerResponse> functionalRoutes(HelloHandler helloHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/functional/mono"), helloHandler::showMessageMono)
                .andRoute(RequestPredicates.GET("/functional/flux"), helloHandler::showFluxMessage);
    }
}
