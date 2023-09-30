package com.domingosuarez.error;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

@Builder
@Getter
@ToString
public class Failure {
    @Builder.Default
    private String code = "default";
    @Builder.Default
    private String reason = "unknown";
    @Builder.Default
    private Optional<Throwable> cause = Optional.empty();
    @Builder.Default
    private Type type = Type.UNEXPECTED;
    @Builder.Default
    private List<Failure> details = List.of();
    @Builder.Default
    private Map<String, Object> failureData = Map.of();

    @Getter
    @Builder
    @ToString
    public static class I18nData {
        @Builder.Default
        private String code = "";
        @Builder.Default
        private String defaultMessage = "";
        @Builder.Default
        private String localizedMessage = "";
        @Builder.Default
        private SortedSet<Object> arguments = new TreeSet<>();

        public static I18nData instance = Failure.I18nData.builder().build();
    }

    public enum Type {
        UNEXPECTED, VALIDATION, BUSINESS
    }

    public static Failure of(List<Failure> failures, Type type) {
        return Failure.builder()
            .type(type)
            .details(failures)
            .build();
    }

    public static Failure validationFailure(String message, Map<String, Object> violationData) {

        return Failure.builder()
            .reason(message)
            .code("validation")
            .failureData(violationData)
            .type(Type.VALIDATION)
            .build();
    }

    public static Failure of(Throwable throwable) {
        return Failure.builder()
            .cause(Optional.of(throwable))
            .reason(throwable.getMessage())
            .build();
    }

    public static Failure of(Throwable throwable, String reason) {
        return Failure.builder()
            .cause(Optional.of(throwable))
            .reason(reason)
            .build();
    }

    public static Failure of(String reason) {
        return Failure.builder()
            .reason(reason)
            .build();
    }

    public static Failure of(String code, String reason) {
        return Failure.builder()
            .code(code)
            .reason(reason)
            .build();
    }
}
