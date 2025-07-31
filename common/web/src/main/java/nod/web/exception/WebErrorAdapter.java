package nod.web.exception;

import nod.core.exception.ErrorResponse;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class WebErrorAdapter implements ErrorResponse.ErrorAdapter {

    private final BindingResult bindingResult;

    public WebErrorAdapter(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    @Override
    public List<ErrorResponse.FieldError> getErrors() {
        return bindingResult.getFieldErrors().stream()
                .map(fieldError -> new ErrorResponse.FieldError(
                        fieldError.getField(),
                        Objects.toString(fieldError.getRejectedValue(), ""),
                        Objects.toString(fieldError.getDefaultMessage(), "")
                ))
                .collect(Collectors.toList());
    }
}