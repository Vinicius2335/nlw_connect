package com.github.vinicius2335.connect.api.utils.creator;

import com.github.vinicius2335.connect.api.domain.event.Event;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.github.vinicius2335.connect.api.utils.FakerUtils.FAKER;

@UtilityClass
public class EventCreator {

    public Event mockEvent() {
        String event = FAKER.esports().event();
        return Event.builder()
                .title("Final " + event)
                .prettyName(
                        event.toLowerCase()
                                .replaceAll(" ", "-")
                )
                .location(FAKER.location().work())
                .price(Double.parseDouble(FAKER.commerce().price().replace(",", ".")))
                .startDate(LocalDate.of(2025, 9, 10))
                .endDate(LocalDate.of(2025, 10, 10))
                .startTime(LocalTime.of(17, 0))
                .endTime(LocalTime.of(21, 0))
                .build();
    }
}
