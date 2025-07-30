package nod.chat.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nod.chat.api.request.LlmRequest;
import nod.chat.api.response.LlmResponse;
import nod.chat.service.LlmChatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/llm")
@RequiredArgsConstructor
public class LlmApi {

    private final LlmChatService chatService;

    @PostMapping("/chat")
    public Mono<LlmResponse> chat(@RequestBody @Valid LlmRequest request) {
        return chatService.chatOnce(request);
    }

    @PostMapping("/stream")
    public Flux<LlmResponse> stream(@RequestBody @Valid LlmRequest request) {
        return chatService.chatStream(request);
    }
}
