package com.reactividad.mongodb.repository;

import com.reactividad.mongodb.documents.Contact;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Solo va crear una sola instancia
public class ContactRepositoryTest {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ReactiveMongoOperations mongoOperations;

    @BeforeAll
    public void insertData() {
        var contact1 = new Contact()
                .setName("Ludwin")
                .setEmail("ludwin.jhurgo@gmail.com")
                .setPhone("519865986532");
        var contact2 = new Contact()
                .setName("Johny")
                .setEmail("jyuyas@gmail.com")
                .setPhone("3562356235");
        var contact3 = new Contact()
                .setName("Luis")
                .setEmail("lsalazar@gmail.com")
                .setPhone("9898656568");

        StepVerifier.create(contactRepository.insert(contact1).log())
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();

        StepVerifier.create(contactRepository.save(contact2).log())
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();

        StepVerifier.create(contactRepository.save(contact3).log())
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @Order(1)
    public void testListContacts() {
        StepVerifier.create(contactRepository.findAll().log())
                .expectSubscription()
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    @Order(2)
    public void testFindByEmail() {
        StepVerifier.create(contactRepository.findFirstByEmail("ludwin.jhurgo@gmail.com").log())
                .expectSubscription()
                .expectNextMatches(contact -> contact.getEmail().equals("ludwin.jhurgo@gmail.com"))
                .verifyComplete();
    }

    @Test
    @Order(3)
    public void testUpdateContact() {
        Mono<Contact> contactUpdated = contactRepository.findFirstByEmail("ludwin.jhurgo@gmail.com")
                .map(contact -> {
                    contact.setPhone("9999999999");
                    return contact;
                }).flatMap(contact -> contactRepository.save(contact));

        StepVerifier.create(contactUpdated.log())
                .expectSubscription()
                .expectNextMatches(contact -> contact.getPhone().equals("9999999999"))
                .verifyComplete();
    }

    @Test
    @Order(4)
    public void testDeleteContact() {
        Mono<Void> contactDeleted = contactRepository.findFirstByEmail("jyuyas@gmail.com")
                .flatMap(contact -> contactRepository.deleteById(contact.getId())).log();

        StepVerifier.create(contactDeleted).expectSubscription()
                .verifyComplete();
    }

    @Test
    @Order(5)
    public void testDeleteAContactByObject() {
        Mono<Void> contactDeleted = contactRepository.findFirstByEmail("jyuyas@gmail.com")
                .flatMap(contact -> contactRepository.delete(contact)).log();

        StepVerifier.create(contactDeleted).expectSubscription()
                .verifyComplete();
    }

    @AfterAll
    public void testClearData() {
        Mono<Void> deletedElements = contactRepository.deleteAll();
        StepVerifier.create(deletedElements).expectSubscription()
                .verifyComplete();
    }

}
