package nod.chat.config;

import nod.chat.client.LlmClient;
import nod.chat.llm.model.LlmType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class LlmConfig {

    @Bean
    public Map<LlmType, LlmClient> llmClientMap(List<LlmClient> llmClients) {
        return llmClients.stream().collect(Collectors.toMap(LlmClient::getLlmType, Function.identity()));
    }
}
