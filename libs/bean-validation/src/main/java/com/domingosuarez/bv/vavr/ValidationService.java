package com.domingosuarez.bv.vavr;

import com.domingosuarez.bv.failure.BeanValidationFailure;
import com.domingosuarez.error.Failure;
import io.vavr.control.Either;
import java.util.function.Function;

public class ValidationService {
    private static final BeanValidationFailure validate = new BeanValidationFailure();

    public <T> Either<Failure, T> validate(T object, Class<?>... groups) {
        return Eithers.withTry(
                () -> Eithers.of(
                    validate.validate(object, groups),
                    object
                ))
            .fold(
                failure -> Eithers.failure(failure, object),
                Function.identity());
    }
}
