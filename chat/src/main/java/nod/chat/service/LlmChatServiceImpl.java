package nod.chat.service;

import lombok.RequiredArgsConstructor;
import nod.chat.api.request.LlmRequest;
import nod.chat.api.response.LlmResponse;
import nod.chat.client.LlmClient;
import nod.chat.llm.model.LlmType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class LlmChatServiceImpl implements LlmChatService {

    private final Map<LlmType, LlmClient> llmClientMap;

    @Override
    public Mono<LlmResponse> chatOnce(LlmRequest request) {
        return null;
    }

    @Override
    public Flux<LlmResponse> chatStream(LlmRequest request) {
        return null;
    }
}
