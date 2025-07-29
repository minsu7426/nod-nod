package nod.auth.config.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import nod.auth.config.security.CustomUser;
import nod.util.jwt.TokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUser principal = (CustomUser) authentication.getPrincipal();

        String accessToken = tokenProvider.createAccessToken(
                principal.getId(),
                principal.getUsername(),
                principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
        );

        createAuthenticationResponse(response, accessToken);
    }

    private void createAuthenticationResponse(HttpServletResponse response, String token) throws IOException {
        response.setHeader(HttpHeaders.AUTHORIZATION, token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());
    }
}
