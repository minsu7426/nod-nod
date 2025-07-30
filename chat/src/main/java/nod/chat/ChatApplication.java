package nod.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
        "nod.chat",
        "nod.webflux",
        "nod.core"
})
@SpringBootApplication
public class ChatApplication {

    public static void main(String[] args) {
      SpringApplication.run(ChatApplication.class, args);
    }
}
