package com.github.vinicius2335.connect.api.domain.event.services;

import com.github.vinicius2335.connect.api.domain.event.Event;
import com.github.vinicius2335.connect.api.domain.event.EventRepository;
import com.github.vinicius2335.connect.api.utils.creator.EventCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DisplayName("Unit test to FindAllEventsService")
class FindAllEventsServiceTest {

    @InjectMocks
    private FindAllEventsService underTest;

    @Mock
    private EventRepository eventRepository;

    private final Event event = EventCreator.mockEvent();

    @Test
    void execute_returnEventList_whenSuccessfully() {
        // given
        Mockito.when(eventRepository.findAll())
                .thenReturn(List.of(event));

        // when
        List<Event> result = underTest.execute();

        // then
        Assertions.assertThat(result)
                .isNotEmpty()
                .isNotNull()
                .hasSize(1)
                .contains(event);

        Mockito.verify(eventRepository, Mockito.times(1))
                .findAll();
    }
}