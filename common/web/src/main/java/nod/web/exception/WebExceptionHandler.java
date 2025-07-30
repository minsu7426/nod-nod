package nod.web.exception;

import lombok.extern.slf4j.Slf4j;
import nod.core.error.CommonError;
import nod.core.exception.BusinessException;
import nod.core.exception.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class WebExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 메소드 유효성 검증이 실패할때 발생한 에러를 처리
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.info("MethodArgumentNotValidException: {}", ex.getMessage());
        ErrorResponse response = ErrorResponse.fromBindingResult(CommonError.INVALID_INPUT_VALUE, new WebErrorAdapter(ex.getBindingResult()));
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * 파라미터가 존재하지 않을 때 발생한 에러를 처리
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.info("MissingServletRequestParameterException: {}", ex.getMessage());
        ErrorResponse response = ErrorResponse.from(CommonError.BAD_REQUEST);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * 요청을 형식을 처리할 수 없을 때 발생한 에러를 처리
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.info("HttpMessageNotReadableException: {}", ex.getMessage());
        ErrorResponse response = ErrorResponse.from(CommonError.BAD_REQUEST);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

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
