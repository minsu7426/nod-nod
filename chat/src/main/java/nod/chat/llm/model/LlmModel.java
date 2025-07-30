package nod.chat.llm.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum LlmModel {
    GEMINI_2_0_FLASH("gemini-2.0-flash", LlmType.GEMINI),
    GEMINI_2_0_FLASH_LIGHT("gemini-2.0-flash-lite", LlmType.GEMINI)
    ;

    private final String name;
    private final LlmType type;

    LlmModel(String name, LlmType type) {
        this.name = name;
        this.type = type;
    }

    public static List<LlmModel> getModelsByType(LlmType type) {
        return Arrays.stream(values())
                .filter(model -> model.getType() == type)
                .collect(Collectors.toList());
    }
}
