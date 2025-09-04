package com.github.vinicius2335.connect.api.domain.event.services;

import com.github.vinicius2335.connect.api.domain.event.Event;
import com.github.vinicius2335.connect.api.domain.event.EventRepository;
import com.github.vinicius2335.connect.api.domain.event.exceptions.EventNotFoundException;
import com.github.vinicius2335.connect.api.utils.creator.EventCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Unit test to FindEventByIdService")
class FindEventByIdServiceTest {

    @InjectMocks
    private FindEventByIdService underTest;

    @Mock
    private EventRepository eventRepository;

    private final Event event = EventCreator.mockEvent();

    @Test
    void execute_returnEvent_whenSuccessfully() throws EventNotFoundException {
        // given
        int eventId = 1;
        Mockito.when(eventRepository.findById(eventId))
                .thenReturn(Optional.of(event));

        // when
        Event result = underTest.execute(eventId);

        // then
        Assertions.assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(event);

        Mockito.verify(eventRepository, Mockito.times(1))
                .findById(eventId);
    }

    @Test
    void execute_throwsException_whenEventNotFoundById() {
        // given
        int eventId = 1;
        Mockito.when(eventRepository.findById(eventId))
                .thenReturn(Optional.empty());

        // when
        Assertions.assertThatThrownBy(
                        () -> underTest.execute(eventId)
                ).isInstanceOf(EventNotFoundException.class)
                .hasMessage("Event not found by id: " + eventId);

        // then
        Mockito.verify(eventRepository, Mockito.times(1))
                .findById(eventId);
    }
}