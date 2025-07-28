package nod.common.global.exception.handler;

import lombok.extern.slf4j.Slf4j;
import nod.common.global.error.CommonError;
import nod.common.global.exception.BusinessException;
import nod.common.global.exception.ErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(2)
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 비즈니스 로직에서 발생한 에러를 처리
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handle(BusinessException e) {
        log.info("BusinessException: {}", e.getMessage());
        ErrorResponse response = ErrorResponse.from(e.getErrorCode());
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handle(RuntimeException e) {
        log.warn("RuntimeException:", e);
        ErrorResponse response = ErrorResponse.from(CommonError.INTERVAL_SERVER_ERROR);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception e) {
        log.error("Exception:", e);
        ErrorResponse response = ErrorResponse.from(CommonError.INTERVAL_SERVER_ERROR);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
