package nod.core.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

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
        return new ErrorResponse(errorCode, adapter.getErrors());
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class FieldError {
        private String field;
        private String value;
        private String reason;
    }

    public interface ErrorAdapter {
        List<FieldError> getErrors();
    }

}
