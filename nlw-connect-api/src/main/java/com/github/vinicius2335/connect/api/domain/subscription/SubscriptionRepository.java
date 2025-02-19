package com.github.vinicius2335.connect.api.domain.subscription;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

    Optional<Subscription> findBySubscribeUserIdAndEventEventId(
            Integer subscribeId,
            Integer eventId
    );
}
