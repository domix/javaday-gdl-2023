package com.domingosuarez.app;

import com.domingosuarez.app.data.PersonRepository;
import com.domingosuarez.app.service.PersonService;
import com.domingosuarez.app.web.CreatePersonCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonServiceTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    PersonRepository repository;
    @Autowired
    PersonService service;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void shouldCreatePerson() {
        final var createPersonCommand = new CreatePersonCommand();
        createPersonCommand.setName("foo");

        final var createPersonResult = service.create(createPersonCommand);

        Assertions.assertTrue(createPersonResult.isRight());
        final var person = createPersonResult.get();
        Assertions.assertEquals(createPersonCommand.getName(), person.getName());
        Assertions.assertTrue(person.getId() > 0, "Id");
    }
}
