package com.github.vinicius2335.connect.api.utils.creator;

import com.github.vinicius2335.connect.api.domain.event.Event;
import com.github.vinicius2335.connect.api.domain.subscription.Subscription;
import com.github.vinicius2335.connect.api.domain.user.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SubscriptionCreator {

    public Subscription mockSubscription(
            User subscribe,
            Event event
    ){
        return Subscription.builder()
                .subscribe(subscribe)
                .event(event)
                .build();
    }

    public Subscription mockSubscription(
            User subscribe,
            Event event,
            User indication
    ){
        return Subscription.builder()
                .subscribe(subscribe)
                .event(event)
                .indication(indication)
                .build();
    }
}
