package nod.common.global.exception;

import org.springframework.http.HttpStatus;

public enum CommonError implements ErrorCode {
    //400
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청 입니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "잘못된 입력 입니다."),

    //500
    INTERVAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 입니다."),
    ;

    private final HttpStatus status;
    private final String message;

    CommonError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getCode() {
        return this.name();
    }

}
