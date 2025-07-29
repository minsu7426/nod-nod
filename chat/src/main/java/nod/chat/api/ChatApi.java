package nod.chat.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ChatApi {

    @GetMapping("/")
    public String index() {
        return "Hello World!";
    }
}
