package nod.common.global.error;

import nod.common.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum UserError implements ErrorCode {

    //400
    DUPLICATE_USER_ID(HttpStatus.BAD_REQUEST, "이미 존재하는 아이디입니다."),
    ;

    private final HttpStatus status;
    private final String message;

    UserError(HttpStatus status, String message) {
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
