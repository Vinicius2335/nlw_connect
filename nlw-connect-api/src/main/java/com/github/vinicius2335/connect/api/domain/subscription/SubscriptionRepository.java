package com.github.vinicius2335.connect.api.domain.subscription;

import com.github.vinicius2335.connect.api.domain.subscription.dtos.SubscriptionRankingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

    Optional<Subscription> findBySubscribeUserIdAndEventEventId(
            Integer subscribeId,
            Integer eventId
    );

    @Query(value = """
            SELECT
                count(subscription_number) AS subscribers,
                user_name AS userName
            FROM tbl_subscription
            INNER JOIN tbl_user ON tbl_subscription.indication_user_id = tbl_user.user_id
            WHERE indication_user_id IS NOT NULL AND event_id = :eventId
            GROUP BY indication_user_id
            ORDER BY subscribers DESC
            LIMIT 3
            """,
            nativeQuery = true
    )
    List<SubscriptionRankingItem> generateRanking(@Param("eventId") Integer eventId);
}
