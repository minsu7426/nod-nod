package nod.chat.api.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import nod.chat.llm.model.LlmModel;

@Getter
@Builder
public class LlmRequest {

    @NotEmpty
    private String request;

    @NotNull
    private LlmModel model;
}
