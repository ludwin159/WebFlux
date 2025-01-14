package com.reactividad.mongodb.controller;

import com.reactividad.mongodb.documents.Contact;
import com.reactividad.mongodb.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ContactController {
    @Autowired
    private ContactRepository contactRepository;

    @GetMapping("/contacts")
    public Flux<Contact> listContact() {
        return contactRepository.findAll();
    }

    @GetMapping("/contacts/{id}")
    public Mono<ResponseEntity<Contact>> getContact(@PathVariable String id) {
        return contactRepository.findById(id)
                .map(contact -> new ResponseEntity<>(contact, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/contacts/byEmail/{email}")
    public Mono<ResponseEntity<Contact>> getContactByEmail(@PathVariable String email) {
        return contactRepository.findFirstByEmail(email)
                .map(contact -> new ResponseEntity<>(contact, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/contacts")
    public Mono<ResponseEntity<Contact>> saveContact(@RequestBody Contact newContact) {
        return contactRepository.insert(newContact)
                .map(savedContact -> new ResponseEntity<>(savedContact, HttpStatus.CREATED))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE));
    }

    @PutMapping("/contacts/{id}")
    public Mono<ResponseEntity<Contact>> updateContact(@RequestBody Contact contact, @PathVariable String id) {
        return contactRepository.findById(id)
                .flatMap(contactFound -> {
                    contact.setId(id);
                    return contactRepository.save(contact);
                })
                .map(contact1 -> new ResponseEntity<>(contact1, HttpStatus.ACCEPTED))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/contacts/{id}")
    public Mono<Void> deleteContact(@PathVariable String id) {
        return contactRepository.deleteById(id);
    }
}
