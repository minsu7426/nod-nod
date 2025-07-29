package nod.webflux.exception;

import nod.core.exception.ErrorResponse;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.List;
import java.util.stream.Collectors;

public class WebFluxErrorAdapter implements ErrorResponse.ErrorAdapter {

    private final WebExchangeBindException exception;

    public WebFluxErrorAdapter(WebExchangeBindException exception) {
        this.exception = exception;
    }

    @Override
    public List<ErrorResponse.AdaptedFieldError> getErrors() {
        return exception.getFieldErrors().stream()
                .map(fieldError ->
                        new ErrorResponse.AdaptedFieldError(
                                fieldError.getField(),
                                fieldError.getRejectedValue(),
                                fieldError.getDefaultMessage()
                        )
                ).collect(Collectors.toList());
    }
}