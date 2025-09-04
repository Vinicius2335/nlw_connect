package com.github.vinicius2335.connect.api.domain.event.services;

import com.github.vinicius2335.connect.api.domain.event.Event;
import com.github.vinicius2335.connect.api.domain.event.EventRepository;
import com.github.vinicius2335.connect.api.domain.event.requests.CreateEventRequest;
import com.github.vinicius2335.connect.api.utils.creator.EventCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("Unit test to CreateEventService")
class CreateEventServiceTest {

    @InjectMocks
    private CreateEventService underTest;

    @Mock
    private EventRepository eventRepository;

    private Event event;

    @BeforeEach
    void setUp() {
        event = EventCreator.mockEvent();
    }

    @Test
    void execute_returnSavedEvent_whenSuccessfully() {
        // given
        CreateEventRequest request = EventCreator.mockCreateEventRequest();
        Mockito.when(eventRepository.save(ArgumentMatchers.any(Event.class)))
                .thenReturn(event);

        // when
        Event result = underTest.execute(request);

        // then
        Assertions.assertThat(result)
                .isNotNull()
                .matches(e -> e.getPrettyName().equals(event.getPrettyName()));

        Mockito.verify(eventRepository, Mockito.times(1))
                .save(ArgumentMatchers.any(Event.class));
    }
}