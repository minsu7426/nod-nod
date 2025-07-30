package nod.webflux.exception;

import lombok.extern.slf4j.Slf4j;
import nod.core.error.CommonError;
import nod.core.exception.BusinessException;
import nod.core.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

@Slf4j
@RestControllerAdvice
public class WebFluxExceptionHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleWebExchangeBindException(WebExchangeBindException ex) {
        log.info("handleWebExchangeBindException: {}", ex.getMessage());
        ErrorResponse response = ErrorResponse.fromBindingResult(CommonError.INVALID_INPUT_VALUE, new WebFluxErrorAdapter(ex));
        return Mono.just(ResponseEntity.status(response.getStatus()).body(response));
    }

    @ExceptionHandler(ServerWebInputException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleDecodingException(ServerWebInputException ex) {
        log.info("handleServerWebInputException: {}", ex.getMessage());
        ErrorResponse response = ErrorResponse.from(CommonError.INVALID_INPUT_VALUE);
        return Mono.just(ResponseEntity.status(response.getStatus()).body(response));
    }

    @ExceptionHandler(BusinessException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleBusinessException(BusinessException ex) {
        log.info("handleBusinessException: {}", ex.getMessage());
        ErrorResponse response = ErrorResponse.from(ex.getErrorCode());
        return Mono.just(ResponseEntity.status(response.getStatus()).body(response));
    }

    @ExceptionHandler(RuntimeException.class)
    public Mono<ResponseEntity<ErrorResponse>> handle(RuntimeException e) {
        log.warn("RuntimeException:", e);
        ErrorResponse response = ErrorResponse.from(CommonError.INTERVAL_SERVER_ERROR);
        return Mono.just(ResponseEntity.status(response.getStatus()).body(response));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponse>> handle(Exception e) {
        log.error("Exception:", e);
        ErrorResponse response = ErrorResponse.from(CommonError.INTERVAL_SERVER_ERROR);
        return Mono.just(ResponseEntity.status(response.getStatus()).body(response));
    }

}