package com.github.vinicius2335.connect.api.domain.subscription;

import com.github.vinicius2335.connect.api.domain.event.Event;
import com.github.vinicius2335.connect.api.domain.event.EventRepository;
import com.github.vinicius2335.connect.api.domain.subscription.dtos.SubscriptionRankingItem;
import com.github.vinicius2335.connect.api.domain.user.User;
import com.github.vinicius2335.connect.api.domain.user.UserRepository;
import com.github.vinicius2335.connect.api.utils.creator.EventCreator;
import com.github.vinicius2335.connect.api.utils.creator.SubscriptionCreator;
import com.github.vinicius2335.connect.api.utils.creator.UserCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;

@DisplayName("Unit test to SubscriptionRepository")
@DataJpaTest
class SubscriptionRepositoryTest {

    @Autowired
    private SubscriptionRepository undertTest;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;

    private Subscription subscription;
    private User user;
    private Event event;
    private int userId;
    private int eventId;

    @BeforeEach
    void setUp() {
        user = UserCreator.mockUser();
        event = EventCreator.mockEvent();
    }

    @Test
    void findBySubscribeUserIdAndEventEventId_returnOptionalSubscription_whenSuccessfully() {
        // given
        Subscription savedSubscription = addSubscriptionWithIndication();
        // when
        Optional<Subscription> result = undertTest.findBySubscribeUserIdAndEventEventId(
                userId, eventId
        );
        // then
        Assertions.assertThat(result)
                .isPresent()
                .hasValueSatisfying(
                        sub -> Assertions.assertThat(sub)
                                .usingRecursiveAssertion()
                                .ignoringFields("subscriptionId")
                                .isEqualTo(savedSubscription)
                );
    }

    @Test
    void generateRanking_returnListSubscriptionRankingItem_whenSuccessfully() {
        // given
        addSubscriptionWithIndication();
        // when
        List<SubscriptionRankingItem> result = undertTest.generateRanking(eventId);
        // then
        Assertions.assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    void generateRanking_returnEmptyList_whenIndicationUserNotExists() {
        // given
        addSubscriptionWithoutIndication();
        // when
        List<SubscriptionRankingItem> result = undertTest.generateRanking(eventId);
        // then
        Assertions.assertThat(result)
                .isEmpty();

    }

    private Subscription addSubscriptionWithIndication(){
        User savedUser = userRepository.save(user);
        userId = savedUser.getUserId();
        Event savedEvent = eventRepository.save(event);
        eventId = savedEvent.getEventId();

        User indication = UserCreator.mockUser();
        userRepository.saveAndFlush(indication);

        subscription = SubscriptionCreator.mockSubscription(user, event, indication);

        return undertTest.save(subscription);
    }

    private Subscription addSubscriptionWithoutIndication(){
        User savedUser = userRepository.save(user);
        userId = savedUser.getUserId();
        Event savedEvent = eventRepository.save(event);
        eventId = savedEvent.getEventId();

        subscription = SubscriptionCreator.mockSubscription(user, event);

        return undertTest.save(subscription);
    }
}