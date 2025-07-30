package nod.chat.api.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LlmResponse {

    private String title;
    private String response;
}
