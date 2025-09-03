package com.github.vinicius2335.connect.api.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Integration test to EventController")
class EventControllerIT extends BaseIT {

    @BeforeEach
    void setUp() {
        configRestAssured("/events");
    }

    @Test
    void createNewEvent() {
    }

    @Test
    void findAllEvents() {
    }

    @Test
    void findEventByPrettyName() {
    }
}