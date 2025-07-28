package nod.common.global.exception.handler;

import lombok.extern.slf4j.Slf4j;
import nod.common.global.exception.CommonError;
import nod.common.global.exception.ErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(2)
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handle(RuntimeException e) {
        System.out.println("GlobalExceptionHandler.RuntimeException");
        log.warn("RuntimeException:", e);
        ErrorResponse response = ErrorResponse.from(CommonError.INTERVAL_SERVER_ERROR);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception e) {
        System.out.println("GlobalExceptionHandler.Exception");
        log.error("Exception:", e);
        ErrorResponse response = ErrorResponse.from(CommonError.INTERVAL_SERVER_ERROR);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
