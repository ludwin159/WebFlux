package com.reactividad.mongodb.functional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ContactRouter {
    @Bean
    public RouterFunction<ServerResponse> contactRoute(ContactHandle contactHandle) {
        return RouterFunctions
                .route(RequestPredicates.GET("/functional/contacts"), contactHandle::listContacts)
                .andRoute(RequestPredicates.GET("/functional/contacts/{id}"), contactHandle::getContactById)
                .andRoute(RequestPredicates.GET("/functional/contacts/byEmail/{email}"), contactHandle::findContactByEmail)
                .andRoute(RequestPredicates.POST("/functional/contacts"), contactHandle::insertAContact)
                .andRoute(RequestPredicates.PUT("/functional/contacts/{id}"), contactHandle::updateContact)
                .andRoute(RequestPredicates.DELETE("/functional/contacts/{id}"), contactHandle::deleteContact);
    }
}
