package nod.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
        "nod.gateway",
        "nod.util",
        "nod.core",
        "nod.webflux"
})
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
      SpringApplication.run(GatewayApplication.class, args);
    }
}
