package com.domingosuarez.app;

import com.domingosuarez.app.data.Person;
import com.domingosuarez.app.data.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest(properties = {
    "spring.test.database.replace=none",
    "spring.datasource.url=jdbc:tc:postgresql:15-alpine:///people"
})
public class PersonRepositoryTest {

    @Autowired
    PersonRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        repository.save(new Person("Foo bar"));
    }

    @Test
    void shouldFindAllPeople() {
        assertThat(repository.findAll()).hasSize(1);
    }
}
