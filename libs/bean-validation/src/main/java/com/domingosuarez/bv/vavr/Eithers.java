package com.domingosuarez.bv.vavr;

import com.domingosuarez.error.Failure;
import io.vavr.CheckedFunction0;
import io.vavr.control.Either;
import io.vavr.control.Try;
import java.util.Optional;

public class Eithers {
    public static <T> Either<Failure, T> success(T result) {
        return Either.right(result);
    }

    public static <T> Either<Failure, T> failure(Failure failure, T ignored) {
        return Either.left(failure);
    }

    public static <T> Either<Failure, T> of(final Optional<Failure> optionalFailure, T t) {
        return optionalFailure
            .map(failure -> Eithers.failure(failure, t))
            .orElseGet(() -> Eithers.success(t));
    }

    public static <T> Either<Failure, T> withTry(final CheckedFunction0<T> supplier) {
        return Try.of(supplier)
            .toEither()
            .mapLeft(Failure::of);
    }
}

