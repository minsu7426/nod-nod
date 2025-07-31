package nod.core.error;

import nod.core.exception.ErrorCode;

public enum UserError implements ErrorCode {

    //400
    DUPLICATE_USER_ID(400, "이미 존재하는 아이디입니다."),
    LOGIN_FAILED(400, "아이디 또는 패스워드가 일치하지 않습니다."),
    ;

    private final int status;
    private final String message;

    UserError(int status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public int getStatus() {
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
