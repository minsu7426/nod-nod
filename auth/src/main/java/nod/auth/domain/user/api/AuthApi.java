package nod.auth.domain.user.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nod.auth.domain.user.request.LoginRequest;
import nod.auth.domain.user.request.RegisterRequest;
import nod.auth.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/v1")
@RequiredArgsConstructor
public class AuthApi {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest request) {
        userService.register(request.userId(), request.password());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginRequest request) {
        return "AuthApi.login";
    }

    @GetMapping("/error")
    public String error() {
        System.out.println("AuthApi.error");
        if (1 == 1) throw new NullPointerException("test exception");
        return "AuthApi.error";
    }

}
