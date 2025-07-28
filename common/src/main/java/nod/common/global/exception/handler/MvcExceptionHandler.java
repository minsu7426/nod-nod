package nod.common.global.exception.handler;

import lombok.extern.slf4j.Slf4j;
import nod.common.global.error.CommonError;
import nod.common.global.exception.ErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@Order(1)
@RestControllerAdvice
public class MvcExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 메소드 유효성 검증이 실패할때 발생한 에러를 처리
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.info("MethodArgumentNotValidException: {}", ex.getMessage());
        ErrorResponse response = ErrorResponse.fromBindingResult(CommonError.INVALID_INPUT_VALUE, ex.getBindingResult());
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

}
