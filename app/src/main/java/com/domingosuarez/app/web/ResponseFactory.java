package com.domingosuarez.app.web;

import com.domingosuarez.error.Failure;
import io.vavr.control.Either;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import org.springframework.http.ResponseEntity;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

public class ResponseFactory {

    public static <T> ResponseEntity<?> of(Either<Failure, T> response) {
        return response.fold(ResponseFactory::of, ResponseEntity::ok);
    }

    public static ResponseEntity<?> of(Failure failure) {
        return Match(failure)
            .of(
                Case($(isValidationFailure()), unprocessableEntity(failure)),
                Case($(isPersonaNonGrata()), personaNonGrata(failure)),
                Case($(isBusinessRuleFailure()), genericBusinessRuleFailure(failure)),
                Case($(), badRequest(failure))
            );
    }

    private static Predicate<Failure> isBusinessRuleFailure() {
        return failure -> Objects.equals(Failure.Type.BUSINESS, failure.getType());
    }

    private static Predicate<Failure> isPersonaNonGrata() {
        return failure -> Objects.equals(Failure.Type.BUSINESS, failure.getType()) &&
            Objects.equals("person.nongrata", failure.getCode());
    }

    private static Predicate<Failure> isValidationFailure() {
        return failure -> Objects.equals(Failure.Type.VALIDATION, failure.getType());
    }

    private static ResponseEntity<?> badRequest(Failure failure) {
        final var failureData = Map.of(
            "code", failure.getCode(),
            "message", failure.getReason()
        );
        return ResponseEntity.badRequest()
            .body(failureData);
    }

    private static ResponseEntity<?> personaNonGrata(Failure failure) {
        final var failureData = Map.of(
            "code", failure.getCode(),
            "message", failure.getReason(),
            "data", failure.getFailureData()
        );
        return ResponseEntity.badRequest()
            .body(failureData);
    }

    private static ResponseEntity<?> genericBusinessRuleFailure(Failure failure) {
        final var failureData = Map.of(
            "code", failure.getCode(),
            "message", failure.getReason()
        );
        return ResponseEntity.badRequest()
            .body(failureData);
    }

    private static ResponseEntity<?> unprocessableEntity(Failure failure) {

        final var failedValidations = failure.getDetails()
            .stream()
            .map(Failure::getFailureData)
            .toList();

        final var failureData = Map.of(
            "code", failure.getCode(),
            "message", failure.getReason(),
            "failedValidations", failedValidations
        );
        return ResponseEntity.unprocessableEntity()
            .body(failureData);
    }
}
