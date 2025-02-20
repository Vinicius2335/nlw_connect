package com.github.vinicius2335.connect.api.domain.subscription.services;

import com.github.vinicius2335.connect.api.domain.event.Event;
import com.github.vinicius2335.connect.api.domain.event.EventNotFoundException;
import com.github.vinicius2335.connect.api.domain.event.services.FindEventByPrettyNameService;
import com.github.vinicius2335.connect.api.domain.subscription.SubscriptionRepository;
import com.github.vinicius2335.connect.api.domain.subscription.dtos.SubscriptionRankingItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenerateRankingByEventService {
    private final SubscriptionRepository subscriptionRepository;
    private final FindEventByPrettyNameService findEventByPrettyNameService;

    public List<SubscriptionRankingItem> execute(String prettyName)
            throws EventNotFoundException {
        Event event = findEventByPrettyNameService.execute(prettyName);

        return subscriptionRepository.generateRanking(event.getEventId());
    }
}
