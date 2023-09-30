package com.domingosuarez.app;

import com.domingosuarez.app.data.Person;
import com.domingosuarez.app.data.PersonRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class PersonControllerTest {
    @LocalServerPort
    private Integer port;

    @Autowired
    PersonRepository personRepository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @BeforeEach
    void setUp() {
        personRepository.deleteAll();
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Test
    void shouldGetAllPeople() {
        List<Person> todos = List.of(
            new Person("foo")
        );
        personRepository.saveAll(todos);

        given()
            .contentType(ContentType.JSON)
            .when()
            .get("/people")
            .then()
            .statusCode(200)
            .body(".", hasSize(1));
    }

    @Test
    void shouldCreatePersonSuccessfully() {
        given()
            .contentType(ContentType.JSON)
            .body(
                """
                    {
                        "name": "SuperSerch"
                    }
                    """
            )
            .when()
            .post("/people")
            .then()
            .statusCode(200)
            .body("name", is("SuperSerch"))
            .body("id", notNullValue());
    }


    @Test
    void shouldPreventToAddChochosBecauseHeIsNotEnoughCoolForUsLaNetflix() {
        given()
            .contentType(ContentType.JSON)
            .body(
                """
                    {
                        "name": "Chochos"
                    }
                    """
            )
            .when()
            .post("/people")
            .then()
            .statusCode(400)
            .body("message", is("No chochos accepted"));
    }
}
