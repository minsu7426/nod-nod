package nod.auth.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/v1")
public class AuthApi {

    @GetMapping("/login")
    public String login() {
        System.out.println("AuthApi.login");
        return "AuthApi.login";
    }

    @GetMapping("/register")
    public String register() {
        System.out.println("AuthApi.register");
        return "AuthApi.register";
    }

}
