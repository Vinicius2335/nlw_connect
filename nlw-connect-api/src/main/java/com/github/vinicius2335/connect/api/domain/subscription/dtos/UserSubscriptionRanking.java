package com.github.vinicius2335.connect.api.domain.subscription.dtos;

public record UserSubscriptionRanking(
        SubscriptionRankingItem item,
        Integer position
) {
}
