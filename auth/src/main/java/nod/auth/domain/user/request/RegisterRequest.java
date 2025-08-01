package nod.auth.domain.user.request;

import jakarta.validation.constraints.NotEmpty;

public record RegisterRequest(
        @NotEmpty String userId,
        @NotEmpty String password
) {
}