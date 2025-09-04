package com.github.vinicius2335.connect.api.domain.event;

import com.github.vinicius2335.connect.api.utils.creator.EventCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Unit test to EventRepository")
class EventRepositoryTest {

    @Autowired
    private EventRepository underTest;

    private Event event;

    @BeforeEach
    void setUp() {
        event = EventCreator.mockEvent();
    }

    @Test
    void findByPrettyName_returnOptionalEvent_whenSuccessfully() {
        // given
        String prettyName = event.getPrettyName();
        underTest.save(event);

        // when
        Optional<Event> result = underTest.findByPrettyName(prettyName);

        // then
        Assertions.assertThat(result)
                .isPresent()
                .map(Event::getPrettyName)
                .hasValue(prettyName);
    }

    @Test
    void findByPrettyName_returnEmptyOptional_whenEventNotFoundByPrettyName() {
        // given
        String prettyName = event.getPrettyName();

        // when
        Optional<Event> result = underTest.findByPrettyName(prettyName);

        // then
        Assertions.assertThat(result)
                .isEmpty();
    }
}