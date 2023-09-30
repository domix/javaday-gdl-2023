package com.domingosuarez.app.service;


import com.domingosuarez.app.web.CreatePersonCommand;
import com.domingosuarez.app.data.Person;
import com.domingosuarez.app.data.PersonRepository;
import com.domingosuarez.bv.vavr.Eithers;
import com.domingosuarez.bv.vavr.ValidationService;
import com.domingosuarez.error.Failure;
import io.vavr.control.Either;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository repository;
    private final ValidationService validationService;

    public Either<Failure, Person> create(CreatePersonCommand inputData) {
        return validationService.validate(inputData)
            .map(command -> new Person(command.getName()))
            .flatMap(this::businessRule1)
            .flatMap(person -> Eithers.withTry(() -> repository.save(person)));
    }
    public Either<Failure, List<Person>> findAll() {
        return Eithers.withTry(repository::findAll);
    }

    public Either<Failure, Person> businessRule1(Person person) {
        if(person.getName().equalsIgnoreCase("chochos")) {
            return noChochos(person);
        }

        return Eithers.success(person);
    }

    private static Either<Failure, Person> noChochos(Person person) {
        Failure noChochosAccepted = Failure.builder()
            .type(Failure.Type.BUSINESS)
            .reason("No chochos accepted")
            .code("person.nongrata")
            .build();
        return Eithers.failure(noChochosAccepted, person);
    }

}
