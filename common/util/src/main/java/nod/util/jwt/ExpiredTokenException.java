package nod.util.jwt;

import io.jsonwebtoken.JwtException;

public class ExpiredTokenException extends JwtException {

    public ExpiredTokenException(String message) {
        super(message);
    }

    public ExpiredTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
