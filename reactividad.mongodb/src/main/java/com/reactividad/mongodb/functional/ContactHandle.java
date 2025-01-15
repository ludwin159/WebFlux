package com.reactividad.mongodb.functional;

import com.reactividad.mongodb.documents.Contact;
import com.reactividad.mongodb.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ContactHandle {
    @Autowired
    private ContactRepository contactRepository;

    private Mono<ServerResponse> response404 = ServerResponse.notFound().build();
    private Mono<ServerResponse> response406 = ServerResponse.status(HttpStatus.ACCEPTED).build();

    // Listar Contactos
    public Mono<ServerResponse> listContacts(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(contactRepository.findAll(), Contact.class);
    }

    // Listar un contacto
    public Mono<ServerResponse> getContactById(ServerRequest request) {
        String id = request.pathVariable("id");
        return contactRepository.findById(id)
                .flatMap(contact -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(contact)))
                .switchIfEmpty(response404);
    }

    // List one contact by email
    public Mono<ServerResponse> findContactByEmail(ServerRequest request) {
        String email = request.pathVariable("email");
        return contactRepository.findFirstByEmail(email)
                .flatMap(contact -> ServerResponse.ok().body(
                        BodyInserters.fromValue(contact)
                ))
                .switchIfEmpty(response404);
    }

    // Save a contact
    public Mono<ServerResponse> insertAContact(ServerRequest request) {
        Mono<Contact> contactSaved = request.bodyToMono(Contact.class);
        return contactSaved
                .flatMap(contact -> contactRepository.save(contact)
                        .flatMap(contactSave -> ServerResponse
                                .accepted()
                                .body(BodyInserters.fromValue(contactSave))));
    }

    // update contact
    public Mono<ServerResponse> updateContact(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Contact> contactMono = request.bodyToMono(Contact.class);

        Mono<Contact> contactUpdated = contactMono
                .flatMap(contact -> contactRepository.findById(id)
                        .flatMap(oldContact -> {
                            oldContact.setName(contact.getName());
                            oldContact.setPhone(contact.getPhone());
                            oldContact.setEmail(contact.getEmail());
                            return contactRepository.save(oldContact);
                        })
                );

        return contactUpdated.flatMap(contact -> ServerResponse
                .accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(contact)))
                .switchIfEmpty(response404);

    }

    public Mono<ServerResponse> deleteContact(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Void> contactDeleted = contactRepository.deleteById(id);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(contactDeleted, Contact.class);
    }
}
