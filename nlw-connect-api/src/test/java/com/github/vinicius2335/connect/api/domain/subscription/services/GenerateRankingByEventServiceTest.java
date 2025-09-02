package com.github.vinicius2335.connect.api.domain.subscription.services;

import com.github.vinicius2335.connect.api.domain.event.Event;
import com.github.vinicius2335.connect.api.domain.event.EventNotFoundException;
import com.github.vinicius2335.connect.api.domain.event.services.FindEventByPrettyNameService;
import com.github.vinicius2335.connect.api.domain.subscription.SubscriptionRepository;
import com.github.vinicius2335.connect.api.domain.subscription.dtos.SubscriptionRankingItem;
import com.github.vinicius2335.connect.api.utils.creator.EventCreator;
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

import java.util.List;

import static org.mockito.Mockito.times;

@DisplayName("Unit test to GenerateRankingByEventService")
@ExtendWith(SpringExtension.class)
class GenerateRankingByEventServiceTest {

    @InjectMocks
    private GenerateRankingByEventService underTest;

    @Mock
    private SubscriptionRepository subscriptionRepository;
    @Mock
    private FindEventByPrettyNameService findEventByPrettyNameService;

    private Event event;

    private String prettyName;
    private Integer eventId;
    private SubscriptionRankingItem subscriptionRankingItem;

    @BeforeEach
    void setUp() {
        event = EventCreator.mockEvent();
        event.setEventId(1);

        prettyName = event.getPrettyName();
        eventId = event.getEventId();

        subscriptionRankingItem = new SubscriptionRankingItem(5L, "username");
    }

    @SneakyThrows
    @Test
    void execute_returnSubscriptionRankingItem_whenSuccessfully() {
        // given
        Mockito.when(findEventByPrettyNameService.execute(prettyName))
                .thenReturn(event);
        Mockito.when(subscriptionRepository.generateRanking(eventId))
                .thenReturn(List.of(subscriptionRankingItem));

        //when
        List<SubscriptionRankingItem> result = underTest.execute(prettyName);

        // then
        Assertions.assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .contains(subscriptionRankingItem);

        Mockito.verify(findEventByPrettyNameService, times(1))
                .execute(prettyName);
        Mockito.verify(subscriptionRepository, times(1))
                .generateRanking(eventId);
    }

    @SneakyThrows
    @Test
    void execute_throwsEventNotFoundException_whenEventNotFoundByPrettyName() {
        // given
        Mockito.doThrow(EventNotFoundException.class)
                .when(findEventByPrettyNameService).execute(prettyName);

        //when
        Assertions.assertThatThrownBy(
                () -> underTest.execute(prettyName)
        ).isInstanceOf(EventNotFoundException.class);

        // then
        Mockito.verify(findEventByPrettyNameService, times(1))
                .execute(prettyName);
        Mockito.verify(subscriptionRepository, times(0))
                .generateRanking(eventId);
    }
}