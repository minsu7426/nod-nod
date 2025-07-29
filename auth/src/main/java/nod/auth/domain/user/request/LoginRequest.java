package nod.auth.domain.user.request;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
        String userId,
        String password
) {
}
