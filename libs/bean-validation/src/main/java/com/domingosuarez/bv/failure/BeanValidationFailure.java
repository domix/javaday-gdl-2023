package com.domingosuarez.bv.failure;

import com.domingosuarez.bv.BeanValidator;
import com.domingosuarez.error.Failure;
import java.util.Map;
import java.util.Optional;

public class BeanValidationFailure {
    private static final BeanValidator validationUtil = new BeanValidator();

    public <T> Optional<Failure> validate(T object, Class<?>... groups) {
        Failure result = null;
        final var validate = validationUtil.validate(object, groups);
        final var failures = validate.stream()
            .map(violation -> {
                final var message = violation.getMessage();
                final var violationData = Map.of(
                    "invalidValue", nonNullOf(violation.getInvalidValue()),
                    "messageTemplate", nonNullOf(violation.getMessageTemplate()),
                    "propertyPath", nonNullOf(violation.getPropertyPath().toString())
                );

                return Failure.validationFailure(message, violationData);
            })
            .toList();

        if (!failures.isEmpty()) {
            result = Failure.of(failures, Failure.Type.VALIDATION);
        }
        return Optional.ofNullable(result);
    }

    private static Object nonNullOf(Object invalidValue) {
        return Optional.ofNullable(invalidValue).orElse("");
    }
}
