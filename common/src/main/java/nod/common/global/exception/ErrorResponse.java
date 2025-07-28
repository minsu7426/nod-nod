package nod.common.global.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

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
    private final List<CustomFieldError> errors;

    private ErrorResponse(ErrorCode errorCode) {
        this(errorCode, Collections.emptyList());
    }

    private ErrorResponse(ErrorCode errorCode, List<CustomFieldError> errors) {
        this.status = errorCode.getStatus().value();
        this.message = errorCode.getMessage();
        this.statusCode = errorCode.getCode();
        this.errors = errors;
    }

    public static ErrorResponse from(ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }

    public static ErrorResponse fromBindingResult(ErrorCode errorCode, BindingResult bindingResult) {
        return new ErrorResponse(errorCode, CustomFieldError.from(bindingResult));
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class CustomFieldError {
        private String field;
        private String value;
        private String reason;

        public static List<CustomFieldError> from(BindingResult bindingResult) {
            return bindingResult.getFieldErrors().stream()
                    .map(e -> new CustomFieldError(
                            e.getField(),
                            Objects.toString(e.getRejectedValue(), ""),
                            e.getDefaultMessage()
                    ))
                    .collect(Collectors.toList());
        }
    }
}
