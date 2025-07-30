package nod.chat.service;

import nod.chat.api.request.LlmRequest;
import nod.chat.api.response.LlmResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LlmChatService {

    Mono<LlmResponse> chatOnce(LlmRequest request);
    Flux<LlmResponse> chatStream(LlmRequest request);
}
