package com.github.vinicius2335.connect.api.domain.subscription.dtos;

public record SubscriptionResponse(
        Integer subscriptionNumber,
        Integer subscriberId,
        String designation
) {
}
