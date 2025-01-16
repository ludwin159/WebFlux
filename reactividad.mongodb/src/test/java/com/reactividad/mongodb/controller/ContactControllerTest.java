package com.reactividad.mongodb.controller;

import com.reactividad.mongodb.documents.Contact;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContactControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    private Contact contactSaved;

    @Test
    @Order(1)
    public void testSaveContact() {
        Flux<Contact> contactFlux = webTestClient.post()
                .uri("/functional/contacts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new Contact("Ludwin", "lsuarez@gmail.com", "9865326598")))
                .exchange()
                .expectStatus().isAccepted()
                .returnResult(Contact.class).getResponseBody()
                .log();

        contactFlux.next().subscribe(contact -> {
            this.contactSaved = contact;
        });
        Assertions.assertNotNull(contactSaved);
    }

    @Test
    @Order(2)
    public void testGetContactByEmail() {
        Flux<Contact> contactFlux = webTestClient.get().uri("/functional/contacts/byEmail/{email}", "lsuarez@gmail.com")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Contact.class).getResponseBody()
                .log();

        StepVerifier.create(contactFlux)
                .expectSubscription()
                .expectNextMatches(contact -> contact.getEmail().equals("lsuarez@gmail.com"))
                .verifyComplete();
    }

    @Test
    @Order(3)
    public void testUpdateContact() {
        Flux<Contact> contactFlux = webTestClient.put()
                .uri("/functional/contacts/{id}", contactSaved.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(
                        new Contact("WebTestClient", "wtc@gmail.com", "1111111")
                                .setId(contactSaved.getId()
                                )
                ))
                .exchange()
                .expectStatus().isAccepted()
                .returnResult(Contact.class).getResponseBody()
                .log();

        StepVerifier.create(contactFlux)
                .expectSubscription()
                .expectNextMatches(contact -> contact.getEmail().equals("wtc@gmail.com"))
                .verifyComplete();
    }

    @Test
    @Order(3)
    public void testListContacts() {
        Flux<Contact> contactFlux = webTestClient.get()
                .uri("/functional/contacts")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(Contact.class)
                .getResponseBody().log();

        StepVerifier.create(contactFlux)
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @Order(4)
    public void testDeleteContact() {
        Flux<Void> flux = webTestClient.delete()
                .uri("/functional/contacts/{id}", contactSaved.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(Void.class).getResponseBody();

        StepVerifier.create(flux)
                .expectSubscription()
                .verifyComplete();
    }
}
