package com.github.vinicius2335.connect.api.domain.subscription.dtos;

public record SubscriptionRankingByUser(
        SubscriptionRankingItem item,
        Integer position
) {
}
