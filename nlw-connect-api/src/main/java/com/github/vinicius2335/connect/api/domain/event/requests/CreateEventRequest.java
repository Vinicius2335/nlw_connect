package com.github.vinicius2335.connect.api.domain.event.requests;

public record CreateEventRequest(
        String name,
        String location,
        Double price,
        String startDate,
        String endDate,
        String startTime,
        String endTime
) {
}
