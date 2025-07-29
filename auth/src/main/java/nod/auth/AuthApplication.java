package nod.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
        "nod.auth",
        "nod.core",
        "nod.util",
        "nod.web"
})
@SpringBootApplication
public class AuthApplication {

    public static void main(String[] args) {
      SpringApplication.run(AuthApplication.class, args);
    }

}
