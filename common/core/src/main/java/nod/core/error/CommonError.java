package nod.core.error;

import nod.core.exception.ErrorCode;

public enum CommonError implements ErrorCode {
    //400
    BAD_REQUEST(400, "잘못된 요청 입니다."),
    INVALID_INPUT_VALUE(400, "잘못된 입력 입니다."),
    LOGIN_FAILED(400, "아이디 또는 패스워드가 일치하지 않습니다."),

    //401
    EXPIRED_TOKEN(401, "만료된 토큰 입니다."),
    MISSING_HEADER(401, "Header가 존재하지 않습니다."),
    INVALID_TOKEN(401, "유효하지 않은 토큰 입니다."),

    //403
    FORBIDDEN(403, "접근 권한이 없습니다."),

    //500
    INTERVAL_SERVER_ERROR(500, "서버 오류 입니다."),
    ;

    private final int status;
    private final String message;

    CommonError(int status, String message) {
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
