package nod.util.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TokenProvider {

    private final long accessTokenSeconds;
    private final SecretKey secretKey;
    private static final String USERNAME_CLAIM = "username";
    private static final String ROLES_CLAIM = "roles";
    private static final String BEARER = "Bearer ";
    private static final String ISSUER = "NOD-NOD";

    public TokenProvider(String secret, long accessTokenSeconds) {
        this.accessTokenSeconds = accessTokenSeconds * 1000;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String createAccessToken(Long userId, String username, List<String> authorities) {
        return Jwts.builder()
                .issuer(ISSUER)
                .subject(userId.toString())
                .claim(USERNAME_CLAIM, username)
                .claim(ROLES_CLAIM, authorities)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessTokenSeconds))
                .signWith(secretKey)
                .compact();
    }

    public String parseBearerToken(String token) {
      return Optional.ofNullable(token)
              .filter(it -> it.startsWith(BEARER))
              .map(it -> it.substring(BEARER.length()).trim())
              .orElseThrow(() -> new MissingJwtTokenException("missing jwt token"));
    }

    private Jws<Claims> validateAndParseToken(String token) throws JwtException {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);
    }

    public String validateTokenAndGetSubject(String token) {
        return this.validateAndParseToken(token)
                .getPayload()
                .getSubject();
    }

    public boolean isTokenExpired(String token) {
        try {
            Date exp = validateAndParseToken(token).getPayload().getExpiration();
            return exp.before(new Date());
        } catch (JwtException e) {
            return true;
        }
    }

    public void validateTokenExpiration(String token) throws ExpiredTokenException {
        Date exp = validateAndParseToken(token).getPayload().getExpiration();
        if (exp.before(new Date())) {
            throw new ExpiredTokenException("expired token");
        }
    }

    public Date validateTokenGetExpirationDate(String token) {
        return this.validateAndParseToken(token)
                .getPayload()
                .getExpiration();
    }

    public String validateTokenGetUsername(String token) {
        return this.validateAndParseToken(token)
                .getPayload()
                .get(USERNAME_CLAIM, String.class);
    }

    public List<String> validateTokenAndGetRoles(String token) {
        Object roleObject = validateAndParseToken(token)
                .getPayload()
                .get(ROLES_CLAIM);

        if (roleObject instanceof List<?> roles) {
            return roles.stream()
                    .map(Object::toString)
                    .toList();
        }

        return Collections.emptyList();
    }
}
