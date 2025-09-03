package com.github.vinicius2335.connect.api.domain.subscription.services;

import com.github.vinicius2335.connect.api.domain.event.Event;
import com.github.vinicius2335.connect.api.domain.event.exceptions.EventNotFoundException;
import com.github.vinicius2335.connect.api.domain.event.services.FindEventByPrettyNameService;
import com.github.vinicius2335.connect.api.domain.subscription.Subscription;
import com.github.vinicius2335.connect.api.domain.subscription.exceptions.SubscriptionConflictException;
import com.github.vinicius2335.connect.api.domain.subscription.SubscriptionRepository;
import com.github.vinicius2335.connect.api.domain.subscription.dtos.SubscriptionResponse;
import com.github.vinicius2335.connect.api.domain.user.User;
import com.github.vinicius2335.connect.api.domain.user.exceptions.UserNotFoundException;
import com.github.vinicius2335.connect.api.domain.user.UserRepository;
import com.github.vinicius2335.connect.api.domain.user.requests.UserSubscriptionRequest;
import com.github.vinicius2335.connect.api.utils.creator.EventCreator;
import com.github.vinicius2335.connect.api.utils.creator.SubscriptionCreator;
import com.github.vinicius2335.connect.api.utils.creator.UserCreator;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@DisplayName("Unit test to CreateSubscriptionService")
@ExtendWith(SpringExtension.class)
class CreateSubscriptionServiceTest {

    @InjectMocks
    private CreateSubscriptionService underTest;

    @Mock
    private SubscriptionRepository subscriptionRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private FindEventByPrettyNameService findEventByPrettyNameService;

    private Subscription subscription;
    private User subscriber;
    private User indicator;
    private Event event;

    private String prettyName;
    private String subscriberEmail;
    private Integer subscriberId;
    private Integer indicatorId;
    private Integer eventId;
    private UserSubscriptionRequest request;
    private SubscriptionResponse expected;

    @BeforeEach
    void setUp() {
        subscriber = UserCreator.mockUser();
        subscriber.setUserId(1);
        indicator = UserCreator.mockUser();
        indicator.setUserId(2);

        event = EventCreator.mockEvent();
        event.setEventId(1);

        subscription = SubscriptionCreator.mockSubscription(subscriber, event);
        subscription.setSubscriptionNumber(1);

        prettyName = event.getPrettyName();
        subscriberEmail = subscriber.getEmail();
        String subscriberName = subscriber.getName();
        subscriberId = subscriber.getUserId();
        indicatorId = indicator.getUserId();
        eventId = event.getEventId();

        request = new UserSubscriptionRequest(subscriberName, subscriberEmail);
        expected = new SubscriptionResponse(1, subscriberId, "");
    }

    @SneakyThrows
    @Test
    void execute_returnSubscriptionResponse_whenSubscriptionIsCreated() {
        // given
        Mockito.when(findEventByPrettyNameService.execute(prettyName))
                .thenReturn(event);
        Mockito.when(userRepository.findByEmail(subscriberEmail))
                .thenReturn(Optional.of(subscriber));
        Mockito.when(subscriptionRepository.findBySubscribeUserIdAndEventEventId(subscriberId, eventId))
                .thenReturn(Optional.empty());
        Mockito.when(userRepository.findById(indicatorId))
                .thenReturn(Optional.of(indicator));
        Mockito.when(subscriptionRepository.save(any(Subscription.class)))
                .thenReturn(subscription);

        // when
        SubscriptionResponse result = underTest.execute(
                prettyName, request, indicatorId
        );
        // then
        Assertions.assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("designation")
                .isEqualTo(expected);

        Mockito.verify(findEventByPrettyNameService, times(1))
                .execute(prettyName);
        Mockito.verify(userRepository, times(1))
                .findByEmail(subscriberEmail);
        Mockito.verify(subscriptionRepository, times(1))
                .findBySubscribeUserIdAndEventEventId(subscriberId, eventId);
        Mockito.verify(userRepository, times(1))
                .findById(indicatorId);
        Mockito.verify(subscriptionRepository, times(1))
                .save(any(Subscription.class));
    }

    @SneakyThrows
    @Test
    void execute_returnSubscriptionResponse_whenSubscriptionIsCreated_andSaveSubscriber() {
        // given
        Mockito.when(findEventByPrettyNameService.execute(prettyName))
                .thenReturn(event);
        Mockito.when(userRepository.findByEmail(subscriberEmail))
                .thenReturn(Optional.empty());
        Mockito.when(userRepository.save(any(User.class)))
                .thenReturn(subscriber);
        Mockito.when(subscriptionRepository.findBySubscribeUserIdAndEventEventId(subscriberId, eventId))
                .thenReturn(Optional.empty());
        Mockito.when(userRepository.findById(indicatorId))
                .thenReturn(Optional.of(indicator));
        Mockito.when(subscriptionRepository.save(any(Subscription.class)))
                .thenReturn(subscription);

        // when
        SubscriptionResponse result = underTest.execute(
                prettyName, request, indicatorId
        );
        // then
        Assertions.assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("designation")
                .isEqualTo(expected);

        Mockito.verify(findEventByPrettyNameService, times(1))
                .execute(prettyName);
        Mockito.verify(userRepository, times(1))
                .findByEmail(subscriberEmail);
        Mockito.verify(userRepository, times(1))
                .save(any(User.class));
        Mockito.verify(subscriptionRepository, times(1))
                .findBySubscribeUserIdAndEventEventId(subscriberId, eventId);
        Mockito.verify(userRepository, times(1))
                .findById(indicatorId);
        Mockito.verify(subscriptionRepository, times(1))
                .save(any(Subscription.class));
    }

    @SneakyThrows
    @Test
    void execute_throwsEventNotFoundException_whenEventNotFoundByPrettyName() {
        // given
        Mockito.doThrow(EventNotFoundException.class)
                .when(findEventByPrettyNameService).execute(prettyName);

        // when
        Assertions.assertThatThrownBy(
                () -> underTest.execute(
                        prettyName, request, indicatorId
                )
        ).isInstanceOf(EventNotFoundException.class);

        // then
        Mockito.verify(findEventByPrettyNameService, times(1))
                .execute(prettyName);

        Mockito.verify(userRepository, times(0))
                .findByEmail(subscriberEmail);
        Mockito.verify(userRepository, times(0))
                .save(any(User.class));
        Mockito.verify(subscriptionRepository, times(0))
                .findBySubscribeUserIdAndEventEventId(subscriberId, eventId);
        Mockito.verify(userRepository, times(0))
                .findById(indicatorId);
        Mockito.verify(subscriptionRepository, times(0))
                .save(any(Subscription.class));
    }

    @SneakyThrows
    @Test
    void execute_throwsSubscriptionConflictException_whenSubscriberAlreadyRegisteredForTheEvent() {
        // given
        Mockito.when(findEventByPrettyNameService.execute(prettyName))
                .thenReturn(event);
        Mockito.when(userRepository.findByEmail(subscriberEmail))
                .thenReturn(Optional.of(subscriber));
        Mockito.when(subscriptionRepository.findBySubscribeUserIdAndEventEventId(subscriberId, eventId))
                .thenReturn(Optional.of(subscription));

        // when
        Assertions.assertThatThrownBy(
                () -> underTest.execute(
                        prettyName, request, indicatorId
                )
        ).isInstanceOf(SubscriptionConflictException.class);

        // then
        Mockito.verify(findEventByPrettyNameService, times(1))
                .execute(prettyName);
        Mockito.verify(userRepository, times(1))
                .findByEmail(subscriberEmail);
        Mockito.verify(subscriptionRepository, times(1))
                .findBySubscribeUserIdAndEventEventId(subscriberId, eventId);

        Mockito.verify(userRepository, times(0))
                .findById(indicatorId);
        Mockito.verify(subscriptionRepository, times(0))
                .save(any(Subscription.class));
    }

    @SneakyThrows
    @Test
    void execute_returnSubscriptionResponse_whenSubscriptionIsCreated_ifIndicationIsNull() {
        // given
        Mockito.when(findEventByPrettyNameService.execute(prettyName))
                .thenReturn(event);
        Mockito.when(userRepository.findByEmail(subscriberEmail))
                .thenReturn(Optional.of(subscriber));
        Mockito.when(subscriptionRepository.findBySubscribeUserIdAndEventEventId(subscriberId, eventId))
                .thenReturn(Optional.empty());
        Mockito.when(userRepository.findById(indicatorId))
                .thenReturn(Optional.of(indicator));
        Mockito.when(subscriptionRepository.save(any(Subscription.class)))
                .thenReturn(subscription);

        // when
        SubscriptionResponse result = underTest.execute(
                prettyName, request, null
        );
        // then
        Assertions.assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("designation")
                .isEqualTo(expected);

        Mockito.verify(findEventByPrettyNameService, times(1))
                .execute(prettyName);
        Mockito.verify(userRepository, times(1))
                .findByEmail(subscriberEmail);
        Mockito.verify(subscriptionRepository, times(1))
                .findBySubscribeUserIdAndEventEventId(subscriberId, eventId);
        Mockito.verify(subscriptionRepository, times(1))
                .save(any(Subscription.class));

        Mockito.verify(userRepository, times(0))
                .findById(indicatorId);
    }

    @SneakyThrows
    @Test
    void execute_throwsUserNotFoundException_whenIndicatorNotFoundById() {
        // given
        Mockito.when(findEventByPrettyNameService.execute(prettyName))
                .thenReturn(event);
        Mockito.when(userRepository.findByEmail(subscriberEmail))
                .thenReturn(Optional.of(subscriber));
        Mockito.when(subscriptionRepository.findBySubscribeUserIdAndEventEventId(subscriberId, eventId))
                .thenReturn(Optional.empty());
        Mockito.when(userRepository.findById(indicatorId))
                .thenReturn(Optional.empty());


        // when
        Assertions.assertThatThrownBy(
                () -> underTest.execute(
                        prettyName, request, indicatorId
                )
        ).isInstanceOf(UserNotFoundException.class);

        // then
        Mockito.verify(findEventByPrettyNameService, times(1))
                .execute(prettyName);
        Mockito.verify(userRepository, times(1))
                .findByEmail(subscriberEmail);
        Mockito.verify(subscriptionRepository, times(1))
                .findBySubscribeUserIdAndEventEventId(subscriberId, eventId);
        Mockito.verify(userRepository, times(1))
                .findById(indicatorId);

        Mockito.verify(subscriptionRepository, times(0))
                .save(any(Subscription.class));
    }
}