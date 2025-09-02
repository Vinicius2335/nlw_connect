package com.github.vinicius2335.connect.api.domain.subscription.services;

import com.github.vinicius2335.connect.api.domain.event.EventNotFoundException;
import com.github.vinicius2335.connect.api.domain.subscription.dtos.UserSubscriptionRanking;
import com.github.vinicius2335.connect.api.domain.subscription.dtos.SubscriptionRankingItem;
import com.github.vinicius2335.connect.api.domain.user.User;
import com.github.vinicius2335.connect.api.domain.user.UserNotFoundException;
import com.github.vinicius2335.connect.api.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenerateRankingByUserService {
    private final UserRepository userRepository;
    private final GenerateRankingByEventService generateRankingByEventService;

    public UserSubscriptionRanking execute(
            String prettyName,
            Integer userId
    ) throws EventNotFoundException, UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found by id: " + userId));

        List<SubscriptionRankingItem> rankingItems = generateRankingByEventService.execute(prettyName);

        SubscriptionRankingItem item = rankingItems.stream()
                .filter(i -> i.userName().equals(user.getName()))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("There are no entries with user indications: " + userId));

        int position = rankingItems.indexOf(item) + 1;

        return new UserSubscriptionRanking(item, position);
    }
}
