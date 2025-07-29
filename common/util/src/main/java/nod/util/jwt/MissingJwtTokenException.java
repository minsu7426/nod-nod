package nod.util.jwt;

import io.jsonwebtoken.JwtException;

public class MissingJwtTokenException extends JwtException {

    public MissingJwtTokenException(String message) {
        super(message);
    }

    public MissingJwtTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
