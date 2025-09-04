package com.github.vinicius2335.connect.api.utils.creator;

import com.github.vinicius2335.connect.api.domain.event.Event;
import com.github.vinicius2335.connect.api.domain.event.requests.CreateEventRequest;
import lombok.experimental.UtilityClass;

import java.time.*;
import java.util.concurrent.TimeUnit;

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

    public CreateEventRequest mockCreateEventRequest(){
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(14);
        ZoneId zoneId = ZoneId.systemDefault();

        Instant startDateTimeInstant = FAKER.timeAndDate().between(
                start.atZone(zoneId).toInstant(),
                end.atZone(zoneId).toInstant()
        );

        LocalDate startDate = startDateTimeInstant.atZone(zoneId).toLocalDate();
        LocalTime startTime = startDateTimeInstant.atZone(zoneId).toLocalTime();

        Instant endDateTimeInstant = FAKER.timeAndDate().between(
                startDateTimeInstant,
                end.atZone(zoneId).toInstant()
        );

        LocalDate endDate = endDateTimeInstant.atZone(zoneId).toLocalDate();
        LocalTime endTime = endDateTimeInstant.atZone(zoneId).toLocalTime();


        return new CreateEventRequest(
                "Event in " + FAKER.company().name(),
                FAKER.address().streetAddress(),
                FAKER.number().randomDouble(2, 10, 1000),
                startDate.toString(),
                endDate.toString(),
                startTime.toString(),
                endTime.toString()
        );
    }


}
