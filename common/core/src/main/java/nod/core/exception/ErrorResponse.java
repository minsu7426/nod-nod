package nod.core.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class ErrorResponse {

    private final int status;
    private final String message;
    private final String statusCode;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<FieldError> errors;

    private ErrorResponse(ErrorCode errorCode) {
        this(errorCode, Collections.emptyList());
    }

    private ErrorResponse(ErrorCode errorCode, List<FieldError> errors) {
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
        this.statusCode = errorCode.getCode();
        this.errors = errors;
    }

    public static ErrorResponse from(ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }

    public static ErrorResponse fromBindingResult(ErrorCode errorCode, ErrorAdapter adapter) {
        return new ErrorResponse(errorCode, FieldError.from(adapter));
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        public static List<FieldError> from(ErrorAdapter adapter) {
            return adapter.getErrors().stream()
                    .map(e -> new FieldError(
                            e.getField(),
                            Objects.toString(e.getRejectedValue(), ""),
                            e.getReason()
                    ))
                    .collect(Collectors.toList());
        }
    }

    public interface ErrorAdapter {
        List<AdaptedFieldError> getErrors();
    }

    @Getter
    @AllArgsConstructor
    public static class AdaptedFieldError {
        private final String field;
        private final Object rejectedValue;
        private final String reason;
    }
}
