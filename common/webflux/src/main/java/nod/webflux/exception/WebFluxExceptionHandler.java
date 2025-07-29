package nod.webflux.exception;

import nod.core.error.CommonError;
import nod.core.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class WebFluxExceptionHandler {

  @ExceptionHandler(WebExchangeBindException.class)
  public Mono<ResponseEntity<ErrorResponse>> handleWebExchangeBindException(WebExchangeBindException ex) {
    ErrorResponse response = ErrorResponse.fromBindingResult(
            CommonError.INVALID_INPUT_VALUE,
            new WebFluxErrorAdapter(ex) // 공통 어댑터 사용
    );

    return Mono.just(ResponseEntity.status(response.getStatus()).body(response));
  }

}