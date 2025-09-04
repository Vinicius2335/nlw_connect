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
@DisplayName("Unit test to FindEventByPrettyNameService")
class FindEventByPrettyNameServiceTest {

    @InjectMocks
    private FindEventByPrettyNameService underTest;

    @Mock
    private EventRepository eventRepository;

    private final Event event = EventCreator.mockEvent();

    @Test
    void execute_returnEvent_whenSuccessfully() throws EventNotFoundException {
        // given
        String prettyName = event.getPrettyName();
        Mockito.when(eventRepository.findByPrettyName(prettyName))
                .thenReturn(Optional.of(event));

        // when
        Event result = underTest.execute(prettyName);

        // then
        Assertions.assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(event);

        Mockito.verify(eventRepository, Mockito.times(1))
                .findByPrettyName(prettyName);
    }

    @Test
    void execute_throwsEventNotFoundException_whenEventNotFoundByPrettyName() {
        // given
        String prettyName = event.getPrettyName();
        Mockito.when(eventRepository.findByPrettyName(prettyName))
                .thenReturn(Optional.empty());

        // when
        Assertions.assertThatThrownBy(
                        () -> underTest.execute(prettyName)
                ).isInstanceOf(EventNotFoundException.class)
                .hasMessage("Event not found by pretty name: " + prettyName);

        // then
        Mockito.verify(eventRepository, Mockito.times(1))
                .findByPrettyName(prettyName);
    }
}