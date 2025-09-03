package com.github.vinicius2335.connect.api.domain.user.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserSubscriptionRequest(
        @NotBlank(message = "VALIDATION.SUBSCRIPTION.NAME.NOT_BLANK")
        String name,

        @Email(message = "VALIDATION.SUBSCRIPTION.EMAIL.INVALID")
        @NotBlank(message = "VALIDATION.SUBSCRIPTION.EMAIL.NOT_BLANK")
        String email
) {
}
