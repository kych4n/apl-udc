package apl.udc.dto.request;

import lombok.Builder;

@Builder
public record SignInRequest(
        String username,
        String password,
        Integer totp
) {
}
